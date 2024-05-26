package baeksaitong.sofp.domain.health.controller;

import baeksaitong.sofp.domain.health.dto.request.DiseaseAllergyEditReq;
import baeksaitong.sofp.domain.health.dto.response.DiseaseAllergyRes;
import baeksaitong.sofp.domain.health.service.DiseaseAllergyService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83E\uDDA0 Disease and Allergy")
@RestController
@RequestMapping("/app/disease-allergy")
@RequiredArgsConstructor
public class DiseaseAllergyController {

    private final DiseaseAllergyService diseaseAllergyService;

    @Operation(summary = "\uD83D\uDD11 질병 및 알레르기 전체 목록 조회", description = "질병 및 알레르기 전체 리스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 전체 리스트")
    })
    @GetMapping
    ResponseEntity<DiseaseAllergyRes> getDiseaseAllergyList(){
        return BaseResponse.ok(diseaseAllergyService.getAllDiseaseAllergyList());
    }

    @Operation(summary = "\uD83D\uDD11 질병 및 알레르기 검색", description = "질병 및 알레르기를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 리스트 검색 결과")
    })
    @GetMapping("/search")
    ResponseEntity<DiseaseAllergyRes> searchDiseaseAllergyList(
            @RequestParam @Schema(description = "검색 키워드") String keyword
    ){
        return BaseResponse.ok(diseaseAllergyService.searchDiseaseAllergyList(keyword));
    }

    @Operation(summary = "\uD83D\uDD11 사용자 질병 및 알레르기 목록 조회", description = "사용자에 대한 질병 및 알레르기 리스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자에 대한 질병 및 알레르기 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{profileId}")
    public ResponseEntity<DiseaseAllergyRes> getDiseaseAllergyList(
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        DiseaseAllergyRes res = diseaseAllergyService.getDiseaseAllergyList(profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 질병 및 알레르기 수정", description = "질병 및 알레르기 정보를 추가 및 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 후 사용자에 대한 질병 및 알레르기 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{profileId}")
    public ResponseEntity<DiseaseAllergyRes> editDiseaseAllergy(
            @RequestBody @Validated DiseaseAllergyEditReq req,
            @PathVariable @Schema(description = "프로필 ID") String profileId){
        DiseaseAllergyRes res = diseaseAllergyService.editDiseaseAllergy(req, profileId);
        return BaseResponse.ok(res);
    }
}
