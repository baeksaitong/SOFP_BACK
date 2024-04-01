package baeksaitong.sofp.domain.auth.feign;

import baeksaitong.sofp.domain.auth.dto.naver.NaverAgreement;
import baeksaitong.sofp.domain.auth.dto.naver.NaverProfile;
import baeksaitong.sofp.domain.auth.dto.naver.NaverToken;
import baeksaitong.sofp.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "naverClient", configuration = FeignConfig.class)
public interface NaverFeignClient {
    @PostMapping
    NaverToken getToken(URI baseUrl,
                        @RequestParam("grant_type") String grantType,
                        @RequestParam("client_id") String clientId,
                        @RequestParam("client_secret") String clientSecret,
                        @RequestParam("code") String code,
                        @RequestParam("state") String state
    );

    @GetMapping
    NaverProfile getProfile(URI baseUrl, @RequestHeader("Authorization") String token);

    @GetMapping
    NaverAgreement getAgreement(URI baseUrl, @RequestHeader("Authorization") String token);

}
