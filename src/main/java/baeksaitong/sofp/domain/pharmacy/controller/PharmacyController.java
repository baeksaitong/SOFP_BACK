package baeksaitong.sofp.domain.pharmacy.controller;

import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyRes;
import baeksaitong.sofp.domain.pharmacy.dto.response.PharmacyRes;
import baeksaitong.sofp.domain.pharmacy.service.PharmacyService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{pharmacyId}")
    public ResponseEntity<PharmacyRes> getPharmacy(@PathVariable String pharmacyId){
        PharmacyRes res = pharmacyService.getPharmacy(pharmacyId);
        return BaseResponse.ok(res);
    }
}
