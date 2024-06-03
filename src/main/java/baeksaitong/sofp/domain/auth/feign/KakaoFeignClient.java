package baeksaitong.sofp.domain.auth.feign;

import baeksaitong.sofp.domain.auth.dto.kakao.KakaoProfile;
import baeksaitong.sofp.domain.auth.dto.kakao.KakaoToken;
import baeksaitong.sofp.global.config.JsonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "kakaoClient", configuration = JsonFeignConfig.class)
public interface KakaoFeignClient {
    @PostMapping
    KakaoToken getToken(URI baseUrl, @RequestParam("grant_type") String grantType,
                        @RequestParam("client_id") String clientId,
                        @RequestParam("redirect_uri") String redirectUri,
                        @RequestParam String code);

    @PostMapping
    KakaoProfile getProfile(URI baseUrl, @RequestHeader("Authorization") String accessToken);
}
