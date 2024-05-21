package baeksaitong.sofp.domain.favorite.controller;

import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteRes;
import baeksaitong.sofp.domain.favorite.service.FavoriteService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "\uD83D\uDD11 즐겨찾기 추가", description = "알약을 즐겨찾기에 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공")
    })
    @PostMapping("/add")
    private ResponseEntity<String> addFavorite(@RequestParam Long profileId, @ModelAttribute @Validated FavoriteReq req){
        favoriteService.addFavorite(req, profileId);
        return BaseResponse.ok("즐겨찾기 추가 성공");
    }

    @Operation(summary = "\uD83D\uDD11 즐겨찾기 삭제", description = "알약을 즐겨찾기에서 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 삭제 성공")
    })
    @GetMapping("/delete")
    private ResponseEntity<String> deleteFavorite(@RequestParam Long pillSerialNumber, @RequestParam Long profileId){
        favoriteService.deleteFavorite(pillSerialNumber, profileId);
        return BaseResponse.ok("즐겨찾기 삭제 성공");
    }

    @Operation(summary = "\uD83D\uDD11 즐겨찾기 조회", description = "즐겨찾기 된 알약을 조회하니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기에 등록된 알약 리스트 획득")
    })
    @GetMapping
    private ResponseEntity<FavoriteRes> getFavorite(@RequestParam Long profileId){
        FavoriteRes res = favoriteService.getFavorite(profileId);
        return BaseResponse.ok(res);
    }
}
