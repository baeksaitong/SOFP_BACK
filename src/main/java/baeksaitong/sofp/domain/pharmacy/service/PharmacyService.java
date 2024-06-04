package baeksaitong.sofp.domain.pharmacy.service;

import baeksaitong.sofp.domain.pharmacy.dto.address.Address;
import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacy;
import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacyInfo;
import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.Pharmacy;
import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.PharmacyInfo;
import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyDto;
import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyRes;
import baeksaitong.sofp.domain.pharmacy.dto.response.PharmacyRes;
import baeksaitong.sofp.domain.pharmacy.error.PharmyErrorCode;
import baeksaitong.sofp.domain.pharmacy.feign.PharmacyFeignClient;
import baeksaitong.sofp.domain.pharmacy.feign.AddressFeignClient;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyService {

    private final PharmacyFeignClient pharmacyFeignClient;
    private final AddressFeignClient addressFeignClient;

    @Value("${api.public-data.serviceKey}")
    private String pharmacyServiceKey;
    @Value("${api.public-data.url.around-pharmacy}")
    private String aroundPharmacyUrl;
    @Value("${api.public-data.url.pharmacy}")
    private String pharmacyUrl;
    @Value("${api.juso.serviceKey}")
    private String addressServiceKey;
    @Value("${api.juso.url.road}")
    private String addressUrl;

    public AroundPharmacyRes getAroundPharmacy(Double longitude, Double latitude, Double distance) {
        List<AroundPharmacyInfo> itemList = pharmacyInfoListByDistance(longitude, latitude, distance);
        List<AroundPharmacyDto> aroundPharmacyList = itemList.stream().map(
                AroundPharmacyDto::new
        ).toList();

        return new AroundPharmacyRes(aroundPharmacyList);
    }

    private List<AroundPharmacyInfo> pharmacyInfoListByDistance(Double longitude, Double latitude, double maxDistance){
        int pageNo = 1;
        List<AroundPharmacyInfo> allPharmacies = new ArrayList<>();

        while (true) {
            AroundPharmacy response = callAroundPharmacyApi(longitude, latitude, pageNo);
            List<AroundPharmacyInfo> pharmacyInfoList = response.getItemList();

            List<AroundPharmacyInfo> filteredPharmacyList = pharmacyInfoList.stream()
                    .filter(pharmacy -> pharmacy.getDistance() < maxDistance)
                    .toList();

            allPharmacies.addAll(filteredPharmacyList);

            if (pharmacyInfoList.stream().anyMatch(pharmacy -> pharmacy.getDistance() >= maxDistance) || filteredPharmacyList.isEmpty()) {
               break;
            }

            int totalCount = response.getTotalCount();
            int numOfRows = response.getNumOfRows();
            int currentPageNo = response.getPageNo();
            int totalPages = (int) Math.ceil((double) totalCount / numOfRows);

            if ( currentPageNo >= totalPages){
                break;
            }

            pageNo++;
        }

        return allPharmacies;
    }

    private AroundPharmacy callAroundPharmacyApi(Double longitude, Double latitude, int pageNo) {
        try {
            AroundPharmacy dto = pharmacyFeignClient.getPharmacyInfoByLocation(new URI(aroundPharmacyUrl), pharmacyServiceKey, longitude, latitude, 100, pageNo);

            if(dto.getStatus() != 0 || dto.getItemList() == null){
                throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
            }

            return dto;
        } catch (URISyntaxException e) {
            throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
        }
    }

    public PharmacyRes getPharmacy(String pharmacyId) {
        PharmacyInfo pharmacyInfo = callPharmacyApi(pharmacyId);
        Address address = callRoadAddressApi(pharmacyInfo.getDutyAddr());

        return new PharmacyRes(
                pharmacyInfo,
                (address.getAddress() == null) ? pharmacyInfo.getDutyAddr() : address.getAddress()
        );
    }

    private PharmacyInfo callPharmacyApi(String hpid) {
        try {
            Pharmacy dto = pharmacyFeignClient.getPharmacyInfo(new URI(pharmacyUrl), pharmacyServiceKey, hpid);

            if(!dto.getStatus()){
                throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
            }

            return dto.getItemList();
        } catch (URISyntaxException e) {
            throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
        }
    }

    private Address callRoadAddressApi(String address){
        try {
            return addressFeignClient.getAddress(new URI(addressUrl), addressServiceKey, 1, 1, address, "json");
        } catch (URISyntaxException e) {
            throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
        }
    }

    public AroundPharmacyRes getNightPharmacy(Double longitude, Double latitude, Double distance) {
        List<AroundPharmacyInfo> itemList = pharmacyInfoListByDistance(longitude, latitude, distance);
        List<AroundPharmacyDto> aroundPharmacyList = itemList.stream()
                .map(AroundPharmacyDto::new)
                .filter(dto -> dto.endTime().isAfter(LocalTime.of(18, 0)))
                .toList();


        return new AroundPharmacyRes(aroundPharmacyList);
    }
}
