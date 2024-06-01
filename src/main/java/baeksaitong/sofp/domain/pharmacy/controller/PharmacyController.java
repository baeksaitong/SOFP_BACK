package baeksaitong.sofp.domain.pharmacy.controller;

import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyRes;
import baeksaitong.sofp.domain.pharmacy.service.PharmacyService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping("/around")
    public ResponseEntity<AroundPharmacyRes> getAroundPharmacy(@RequestParam Double longitude, @RequestParam Double latitude){
        AroundPharmacyRes res = pharmacyService.getAroundPharmacy(longitude, latitude);
        return BaseResponse.ok(res);
    }
}
