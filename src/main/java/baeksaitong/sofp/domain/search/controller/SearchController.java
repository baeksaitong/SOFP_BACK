package baeksaitong.sofp.domain.search.controller;

import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.service.SearchService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83D\uDD0D️ Search")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/search")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "\uD83D\uDD11 모양 및 검색어 검색", description = "모양 및 검색어로 알약을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과에 따른 알약 리스트 및 경고 여부를 제공합니다.")
    })
    @PostMapping("/keyword")
    public ResponseEntity<KeywordRes> findByKeyword(@RequestBody KeywordReq req, @AuthenticationPrincipal Member member){
        KeywordRes res = searchService.findByKeyword(req, member);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 알약 세부 정보", description = "알약 세부 정보를 제공 합니다." )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알약 세부 사항 및 경고 사항을 알려줍니다."),
            @ApiResponse(responseCode = "404", description = "code: S-000 | message: 알약 정보를 불려오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/info")
    public ResponseEntity<PillInfoRes> getPillDetailInfo(@RequestParam String serialNumber, @AuthenticationPrincipal Member member){
        PillInfoRes res = searchService.getPillInfo(serialNumber, member);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 이미지 검색", description = "이미지로 알약을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과에 따른 알약 리스트 및 경고 여부를 제공합니다.")
    })
    @PostMapping("/image")
    public ResponseEntity<?> findByImage(@ModelAttribute ImageReq req){
        searchService.findByImage(req);
        return BaseResponse.ok("임시");
    }
}
