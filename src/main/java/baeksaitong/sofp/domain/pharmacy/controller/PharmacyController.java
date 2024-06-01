package baeksaitong.sofp.domain.pharmacy.controller;

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
    public ResponseEntity<String> getAroundPharmacy(@RequestParam Double x, @RequestParam Double y){
        pharmacyService.getAroundPharmacy(x, y);
        return BaseResponse.ok("전송 성공");
    }
}
