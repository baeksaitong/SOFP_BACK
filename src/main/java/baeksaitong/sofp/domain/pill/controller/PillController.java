package baeksaitong.sofp.domain.pill.controller;

import baeksaitong.sofp.domain.pill.dto.request.MovePillReq;
import baeksaitong.sofp.domain.pill.dto.request.PillReq;
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

    @GetMapping
    public ResponseEntity<PillRes> getPillList(
            @RequestParam @Schema(name = "프로필 ID") Long profileId,
            @RequestParam(required = false) Long categoryId
    ){
        PillRes res = pillService.getPillList(profileId, categoryId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 복용중인 알약 추가", description = "알약 시리얼 번호 리스트를 사용하여 복용중인 알약을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알약이 성공적으로 추가 됬습니다."),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<String> addPill(
            @RequestBody @Validated PillReq req,
            @RequestParam @Schema(name = "프로필 ID") Long profileId){
        pillService.addPill(req, profileId);
        return BaseResponse.ok("알약 추가에 성공했습니다.");
    }

    @Operation(summary = "\uD83D\uDD11 복용중인 알약 삭제", description = "알약 시리얼 번호 리스트를 사용하여 등록한 복용중인 알약을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알약이 성공적으로 삭제됬습니다."),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/delete")
    public ResponseEntity<String> removePill(
            @RequestBody @Validated PillReq req,
            @RequestParam @Schema(name = "프로필 ID") Long profileId
    ){
        pillService.removePill(req, profileId);
        return BaseResponse.ok("알약 삭제에 성공했습니다.");
    }

    @PostMapping("/move")
    public ResponseEntity<String> movePill(
        @RequestBody MovePillReq req,
        @RequestParam Long profileId
    ){
        pillService.movePill(req, profileId);
        return BaseResponse.ok("알약 이동에 성공했습니다.");
    }
}
