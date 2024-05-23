package baeksaitong.sofp.domain.pill.controller;

import baeksaitong.sofp.domain.pill.dto.request.PillReq;
import baeksaitong.sofp.domain.pill.dto.response.PillCategoryRes;
import baeksaitong.sofp.domain.pill.dto.response.PillMainRes;
import baeksaitong.sofp.domain.pill.dto.response.PillRes;
import baeksaitong.sofp.domain.pill.service.PillService;
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

@Tag(name = "\uD83D\uDC8A Pill")
@RestController
@RequestMapping("/app/pill")
@RequiredArgsConstructor
public class PillController {
    private final PillService pillService;

    @GetMapping("/main")
    public ResponseEntity<PillMainRes> getPillList(
            @RequestParam @Schema(name = "프로필 ID") Long profileId
    ){
        PillMainRes res = pillService.getPillList(profileId);
        return BaseResponse.ok(res);
    }

    @GetMapping("/category")
    public ResponseEntity<PillCategoryRes> getPillListByCategory(
            @RequestParam Long categoryId
    ){
        PillCategoryRes res = pillService.getPillListByCategory(categoryId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 복용중인 알약 추가", description = "알약 시리얼 번호 리스트를 사용하여 복용중인 알약을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추가 후 복용중인 알약 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<PillRes> addPill(
            @RequestBody @Validated PillReq req,
            @RequestParam @Schema(name = "프로필 ID") Long profileId){
        PillRes res = pillService.addPill(req, profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 복용중인 알약 삭제", description = "등록한 복용중인 알약을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 후 복용중인 알약 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/delete")
    public ResponseEntity<PillRes> removePill(
            @RequestBody @Validated PillReq req,
            @RequestParam @Schema(name = "프로필 ID") Long profileId){
        PillRes res = pillService.removePill(req, profileId);
        return BaseResponse.ok(res);
    }
}
