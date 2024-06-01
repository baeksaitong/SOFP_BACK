package baeksaitong.sofp.domain.pharmacy.service;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacy;
import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacyInfo;
import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyDto;
import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyRes;
import baeksaitong.sofp.domain.pharmacy.error.PharmyErrorCode;
import baeksaitong.sofp.domain.pharmacy.feign.PharmacyFeignClient;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyService {

    private final PharmacyFeignClient pharmacyFeignClient;

    @Value("${api.public-data.serviceKey}")
    private String serviceKey;
    @Value("${api.public-data.url.around-pharmacy}")
    private String aroundPharmacyUrl;
    public AroundPharmacyRes getAroundPharmacy(Double longitude, Double latitude) {
        List<AroundPharmacyDto> aroundPharmacyList = callAroundPharmacyApi(longitude, latitude).stream().map(
                AroundPharmacyDto::new
        ).toList();

        return new AroundPharmacyRes(aroundPharmacyList);
    }

    private List<AroundPharmacyInfo> callAroundPharmacyApi(Double longitude, Double latitude) {
        try {
            AroundPharmacy dto = pharmacyFeignClient.getPharmacyInfoByLocation(new URI(aroundPharmacyUrl), serviceKey, longitude, latitude);

            if(dto.getStatus() != 0 || dto.getItemList() == null){
                throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
            }

            return dto.getItemList();
        } catch (URISyntaxException e) {
            throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
        }
    }
}
