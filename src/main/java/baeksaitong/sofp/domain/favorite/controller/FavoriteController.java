package baeksaitong.sofp.domain.favorite.controller;

import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.service.FavoriteService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app/favorite")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/add")
    private ResponseEntity<?> addFavorite(@ModelAttribute FavoriteReq req, @AuthenticationPrincipal Member member){
        favoriteService.addFavorite(req, member);
        return BaseResponse.ok("즐겨찾기 추가 성공");
    }
}
