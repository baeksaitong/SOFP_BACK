package baeksaitong.sofp.domain.health.controller;

import baeksaitong.sofp.domain.health.dto.response.AllergyRes;
import baeksaitong.sofp.domain.health.dto.response.DiseaseRes;
import baeksaitong.sofp.domain.health.service.HealthService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping("/allergy")
    ResponseEntity<AllergyRes> getAllergyList(){
        return BaseResponse.ok(healthService.getAllergyList());
    }

    @GetMapping("/disease")
    ResponseEntity<DiseaseRes> getDiseaseList(){
        return BaseResponse.ok(healthService.getDiseaseList());
    }
}
