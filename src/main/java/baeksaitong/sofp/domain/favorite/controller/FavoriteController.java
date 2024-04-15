package baeksaitong.sofp.domain.favorite.controller;

import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteRes;
import baeksaitong.sofp.domain.favorite.service.FavoriteService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/app/favorite")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(tags = "6. Favorite", summary = "즐겨찾기 추가", description = "알약을 즐겨찾기에 추가합니다." +
            "<br> - 인증 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공")
    })
    @PostMapping("/add")
    private ResponseEntity<?> addFavorite(@ModelAttribute FavoriteReq req, @AuthenticationPrincipal Member member){
        favoriteService.addFavorite(req, member);
        return BaseResponse.ok("즐겨찾기 추가 성공");
    }

    @Operation(tags = "6. Favorite", summary = "즐겨찾기 삭제", description = "알약을 즐겨찾기에서 삭제합니다." +
            "<br> - 인증 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 삭제 성공")
    })
    @GetMapping("/delete")
    private ResponseEntity<?> deleteFavorite(@RequestParam Long favoriteId){
        favoriteService.deleteFavorite(favoriteId);
        return BaseResponse.ok("즐겨찾기 삭제 성공");
    }

    @Operation(tags = "6. Favorite", summary = "즐겨찾기 조회", description = "즐겨찾기 된 알약을 조회하니다." +
            "<br> - 인증 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기에 등록된 알약 리스트 획득")
    })
    @GetMapping
    private ResponseEntity<?> getFavorite(@AuthenticationPrincipal Member member){
        List<FavoriteRes> res = favoriteService.getFavorite(member);
        return BaseResponse.ok(res);
    }
}
