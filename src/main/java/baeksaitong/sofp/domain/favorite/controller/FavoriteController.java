package baeksaitong.sofp.domain.favorite.controller;

import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteRes;
import baeksaitong.sofp.domain.favorite.service.FavoriteService;
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

@Tag(name = "⭐ Favorite")
@RequestMapping("/app/favorite")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "\uD83D\uDD11 즐겨찾기 추가", description = "알약 시리얼 번호로 즐겨찾기를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공"),
            @ApiResponse(responseCode = "404", description = "code: P-000 | message: 존재하지 않는 알약입니다. <br>" +
                    "code: U-001 | message: 프로필이 존재하지 않습니다. <br>" +
                    "code: F-000 | message: 이미 즐겨찾기에 추가되어 있습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/{profileId}",  consumes = "multipart/form-data")
    private ResponseEntity<String> addFavorite(
            @PathVariable @Schema(description = "프로필 ID") String profileId,
            @ModelAttribute @Validated FavoriteReq req
    ){
        favoriteService.addFavorite(req, profileId);
        return BaseResponse.ok("즐겨찾기 추가 성공");
    }

    @Operation(summary = "\uD83D\uDD11 즐겨찾기 삭제", description = "알약 시리얼 번호로 즐겨찾기에서 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "code: P-000 | message: 존재하지 않는 알약입니다. <br>" +
                    "code: U-001 | message: 프로필이 존재하지 않습니다. <br>" +
                    "code: F-001 | message: 해당 즐겨찾기 정보가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{profileId}")
    private ResponseEntity<String> deleteFavorite(
            @PathVariable @Schema(description = "프로필 ID") String profileId,
            @RequestParam @Schema(description = "알약 시리얼 번호") Long pillSerialNumber
    ){
        favoriteService.deleteFavorite(pillSerialNumber, profileId);
        return BaseResponse.ok("즐겨찾기 삭제 성공");
    }

    @Operation(summary = "\uD83D\uDD11 즐겨찾기 조회", description = "즐겨찾기에 추가된 알약 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기에 등록된 알약 리스트 획득"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{profileId}")
    private ResponseEntity<FavoriteRes> getFavorite(
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        FavoriteRes res = favoriteService.getFavorite(profileId);
        return BaseResponse.ok(res);
    }
}
