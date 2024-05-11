package baeksaitong.sofp.domain.health.controller;

import baeksaitong.sofp.domain.health.service.DiseaseAllergyService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "\uD83E\uDDA0 Disease and Allergy")
@RestController
@RequestMapping("/app/disease-allergy")
@RequiredArgsConstructor
public class DiseaseAllergyController {

    private final DiseaseAllergyService diseaseAllergyService;

    @Operation(summary = "질병 및 알레르기 목록 조회", description = "질병 및 알레르기 리스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 리스트를 가져옵니다.")
    })
    @GetMapping()
    ResponseEntity<List<String>> getDiseaseAllergyList(){
        return BaseResponse.ok(diseaseAllergyService.getDiseaseAllergyList());
    }

    @Operation(summary = "질병 및 알레르기 검색", description = "질병 및 알레르기를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 리스트 검색 결과")
    })
    @GetMapping("/search")
    ResponseEntity<List<String>> searchDiseaseAllergyList(@RequestParam String keyword){
        return BaseResponse.ok(diseaseAllergyService.searchDiseaseAllergyList(keyword));
    }
}
