package baeksaitong.sofp.domain.pharmacy.service;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacyDto;
import baeksaitong.sofp.domain.pharmacy.error.PharmyErrorCode;
import baeksaitong.sofp.domain.pharmacy.feign.PharmacyFeignClient;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyService {

    private final PharmacyFeignClient pharmacyFeignClient;

    @Value("${api.public-data.serviceKey}")
    private String serviceKey;
    @Value("${api.public-data.url.around-pharmacy}")
    private String aroundPharmacyUrl;
    public void getAroundPharmacy(Double longitude, Double latitude) {
        callAroundPharmacyApi(longitude, latitude);
    }

    private AroundPharmacyDto callAroundPharmacyApi(Double longitude, Double latitude) {
        try {
            AroundPharmacyDto dto = pharmacyFeignClient.getPharmacyInfoByLocation(new URI(aroundPharmacyUrl), serviceKey, longitude, latitude);

            if(dto.getStatus() != 0 || dto.getItemList() == null){
                throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
            }

            return dto;
        } catch (URISyntaxException e) {
            throw new BusinessException(PharmyErrorCode.PHARMY_INFO_ERROR);
        }
    }
}
