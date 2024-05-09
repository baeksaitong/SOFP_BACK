package baeksaitong.sofp.domain.health.controller;

import baeksaitong.sofp.domain.health.dto.response.DiseaseRes;
import baeksaitong.sofp.domain.health.service.HealthService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(tags = "4. Health", summary = "질병 및 알레르기 목록 조회", description = "질병 및 알레르기 리스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 리스트를 가져옵니다.")
    })
    @GetMapping("/disease")
    ResponseEntity<DiseaseRes> getDiseaseList(){
        return BaseResponse.ok(healthService.getDiseaseAndAllergyList());
    }
}
