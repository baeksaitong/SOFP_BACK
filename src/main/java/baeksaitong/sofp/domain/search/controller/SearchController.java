package baeksaitong.sofp.domain.search.controller;

import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.search.service.SearchService;
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

@Tag(name = "\uD83D\uDD0D️ Search")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/search")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "\uD83D\uDD11 모양 및 검색어 검색", description = "모양 및 검색어로 알약을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과에 따른 알약 리스트 및 경고 여부를 제공합니다."),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "code : S-000 | message : 알약 정보를 불려오는데 실패했습니다. <br>" +
                    "code : S-001 | message : 알약 정보가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/keyword")
    public ResponseEntity<KeywordRes> findByKeyword(
            @RequestParam @Schema(description = "프로필 ID") Long profileId,
            @RequestParam(required = false) @Schema(description = "검색 키워드 - 알약 이름, 성분, 효능 정보",example = "가스디알정") String keyword,
            @RequestParam(required = false) @Schema(description = "알약 모양", example = "원형") String shape,
            @RequestParam(required = false) @Schema(description = "알약에 적힌 기호", example = "IDG") String sign,
            @RequestParam(required = false) @Schema(description = "알약 색깔", example = "연두") String color,
            @RequestParam(required = false) @Schema(description = "알약 제형", example = "경질/연질/정제") String formulation,
            @RequestParam(required = false) @Schema(description = "알약 분할선", example = "-/+") String line
    ){
        KeywordRes res = searchService.findByKeyword(profileId, keyword, shape, sign, color, formulation, line);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 알약 세부 정보", description = "알약 세부 정보를 제공 합니다." )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알약 세부 사항 및 경고 사항을 알려줍니다."),
            @ApiResponse(responseCode = "500", description = "code : S-000 | message : 알약 정보를 불려오는데 실패했습니다. <br>" +
                    "code : S-001 | message : 알약 정보가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{pillSerialNumber}")
    public ResponseEntity<PillInfoRes> getPillDetailInfo(
            @PathVariable @Schema(description = "알약 시리얼 번호") String pillSerialNumber,
            @RequestParam @Schema(description = "프로필 ID") Long profileId
    ){
        PillInfoRes res = searchService.getPillInfo(pillSerialNumber, profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 이미지 검색", description = "이미지로 알약을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과에 따른 알약 리스트 및 경고 여부를 제공합니다.")
    })
    @PostMapping("/image")
    public ResponseEntity<?> findByImage(@ModelAttribute @Validated ImageReq req){
        searchService.findByImage(req);
        return BaseResponse.ok(req.getPage().toString() + ", " + req.getLimit().toString());
    }
}
