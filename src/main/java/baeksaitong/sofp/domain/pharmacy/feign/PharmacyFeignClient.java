package baeksaitong.sofp.domain.pharmacy.feign;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacy;
import baeksaitong.sofp.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "PharmyClient", configuration = FeignConfig.class)
public interface PharmacyFeignClient {
    @GetMapping
    AroundPharmacy getPharmacyInfoByLocation(URI baseUrl,
                                             @RequestParam String serviceKey,
                                             @RequestParam(name = "WGS84_LON") Double longitude,
                                             @RequestParam(name = "WGS84_LAT") Double latitude);
}
