package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.service.KakaoService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final KakaoService kakaoService;

    @GetMapping("/kakao")
    public ResponseEntity<LoginRes> kakao(String code){
        return BaseResponse.ok(new LoginRes(kakaoService.login(code)));
    }
}
