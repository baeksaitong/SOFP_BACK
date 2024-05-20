package baeksaitong.sofp.domain.health.controller;

import baeksaitong.sofp.domain.health.service.DiseaseAllergyService;
import baeksaitong.sofp.domain.health.dto.request.DiseaseAllergyEditReq;
import baeksaitong.sofp.domain.health.dto.response.DiseaseAllergyRes;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/all")
    ResponseEntity<List<String>> getDiseaseAllergyList(){
        return BaseResponse.ok(diseaseAllergyService.getAllDiseaseAllergyList());
    }

    @Operation(summary = "질병 및 알레르기 검색", description = "질병 및 알레르기를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 리스트 검색 결과")
    })
    @GetMapping("/search")
    ResponseEntity<List<String>> searchDiseaseAllergyList(@RequestParam String keyword){
        return BaseResponse.ok(diseaseAllergyService.searchDiseaseAllergyList(keyword));
    }

    @GetMapping
    public ResponseEntity<DiseaseAllergyRes> getDiseaseAllergyList(@RequestParam String name, @AuthenticationPrincipal Member member){
        DiseaseAllergyRes res = diseaseAllergyService.getDiseaseAllergyList(name, member);
        return BaseResponse.ok(res);
    }

    @PostMapping("/edit")
    public ResponseEntity<DiseaseAllergyRes> editDiseaseAllergy(@RequestParam String name, @RequestBody DiseaseAllergyEditReq req, @AuthenticationPrincipal Member member){
        DiseaseAllergyRes res = diseaseAllergyService.editDiseaseAllergy(name, req, member);
        return BaseResponse.ok(res);
    }
}
