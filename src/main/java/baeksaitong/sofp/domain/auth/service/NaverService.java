package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.naver.NaverProfile;
import baeksaitong.sofp.domain.auth.dto.naver.NaverToken;
import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.auth.feign.NaverFeignClient;
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
public class NaverService {

    private final NaverFeignClient naverFeignClient;
    private final AuthService authService;

    @Value("${social.naver.client.id}")
    private String clientId;

    @Value("${social.naver.client.secret}")
    private String clientSecret;

    @Value("${social.naver.state}")
    private String state;
    @Value("${social.naver.url.token}")
    private String tokenUrl;
    @Value("${social.naver.url.profile}")
    private String profileUrl;

    public LoginRes login(String code) {
        NaverToken token = getToken(code);
        NaverProfile profile = getInfo(token.accessToken());

        return authService.oauthLogin(profile.getEmail(), profile.getId(), profile.getBirthday(), profile.getName(), profile.getGender());

    }

    private NaverToken getToken(String code){
        try {
            return naverFeignClient.getToken(new URI(tokenUrl), OAUTH_GRANT_TYPE, clientId, clientSecret, code, state);
        } catch (Exception e) {
            log.error("error while getting naver user token: ", e);
            throw new BusinessException(AuthErrorCode.NAVER_ERROR);
        }
    }

    private NaverProfile getInfo(String token){
        try {
            return naverFeignClient.getProfile(new URI(profileUrl), "Bearer " + token);
        } catch (Exception e) {
            log.error("error while getting naver user profile: ", e);
            throw new BusinessException(AuthErrorCode.NAVER_ERROR);
        }
    }
}
