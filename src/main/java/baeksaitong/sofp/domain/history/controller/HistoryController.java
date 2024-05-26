package baeksaitong.sofp.domain.history.controller;

import baeksaitong.sofp.domain.history.dto.response.HistoryRes;
import baeksaitong.sofp.domain.history.service.HistoryService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83E\uDDFE History")
@RestController
@RequestMapping("/app/recent-view")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @Operation(summary = "\uD83D\uDD11 최근 본 알약 조회", description = "요청 한 갯수에 맞춰 최근 조회 한 알약 정보 리스트를 제공합니다. <br>" +
            "- 최대 40개까지 조회가 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최근 본 알약 정보 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{profileId}")
    public ResponseEntity<HistoryRes> getRecentViewPill(
            @PathVariable @Schema(description = "프로필 ID") String profileId,
            @Schema(description = "조회할 요소 개수 - 입력 가능 범위 : 1~40")
            @Size(min = 1, max = 40, message = "조회 요소 범위는 1 부터 40 이내의 정수입니다.") @RequestParam int count
    ){
        HistoryRes res = historyService.getRecentViewPill(count, profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 최근 본 알약 삭제", description = "최근 본 알약 요소를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최근 본 알약 정보 리스트"),
            @ApiResponse(responseCode = "404", description = "code: H-000 | message: 최근 본 알약 리스트가 존재하지 않습니다.<br>" +
                    "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deleteRecentViewPill(
            @PathVariable @Schema(description = "프로필 ID") String profileId,
            @RequestParam @Schema(description = "알약 시리얼 번호") Long pillSerialNumber){
        historyService.deleteRecentViewPill(pillSerialNumber, profileId);
        return BaseResponse.ok("최근 본 알약 삭제에 성공 했습니다.");
    }
}
