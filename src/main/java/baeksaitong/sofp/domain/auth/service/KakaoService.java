package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.kakao.KakaoProfile;
import baeksaitong.sofp.domain.auth.dto.kakao.KakaoToken;
import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.auth.feign.KakaoFeignClient;
import baeksaitong.sofp.domain.profile.entity.enums.Gender;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

import static baeksaitong.sofp.global.common.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoFeignClient kakaoFeignClient;
    private final AuthService authService;


    @Value("${social.kakao.client.id}")
    private String clientId;
    @Value("${social.kakao.url.redirect}")
    private String redirectUrl;
    @Value("${social.kakao.url.token}")
    private String tokenUrl;
    @Value("${social.kakao.url.profile}")
    private String profileUrl;

    public LoginRes login(String code) {
        KakaoToken kakaoToken = getToken(code);
        KakaoProfile profile = getProfile(kakaoToken.accessToken());

        return authService.oauthLogin(
                profile.getEmail(),
                profile.getId().toString(),
                profile.getBirthday(),
                profile.getName(),
                (profile.getGender().equals("male")) ? Gender.MALE : Gender.FEMALE,
                Boolean.TRUE
        );
    }

    private KakaoProfile getProfile(String token){
        try {
            return kakaoFeignClient.getProfile(new URI(profileUrl), "bearer " + token);
        } catch (Exception e) {
            log.error("error while getting kakao user info: ", e);
            throw new BusinessException(AuthErrorCode.KAKAO_ERROR);
        }
    }


    private KakaoToken getToken(String code) {
        try {
            return kakaoFeignClient.getToken(new URI(tokenUrl), OAUTH_GRANT_TYPE, clientId, redirectUrl, code);
        } catch (Exception e) {
            log.error("error while getting kakao user token: ", e);
            throw new BusinessException(AuthErrorCode.KAKAO_ERROR);
        }
    }
}
