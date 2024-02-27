package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.kakao.KakaoProfile;
import baeksaitong.sofp.domain.auth.dto.kakao.KakaoToken;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.auth.feign.KakaoFeignClient;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoFeignClient kakaoFeignClient;
    private final MemberRepository memberRepository;
    private final AuthService authService;


    @Value("${social.kakao.secret.client-id}")
    private String clientId;
    @Value("${social.kakao.redirect-url}")
    private String redirectUrl;
    @Value("${social.kakao.token-url}")
    private String tokenUrl;
    @Value("${social.kakao.profile-url}")
    private String profileUrl;

    public String login(String code) {
        KakaoToken kakaoToken = getToken(code);
        KakaoProfile profile = getProfile(kakaoToken.accessToken());
        return "테스트";
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
            return kakaoFeignClient.getToken(new URI(tokenUrl),"authorization_code", clientId, redirectUrl, code);
        } catch (Exception e) {
            log.error("error while getting kakao user token: ", e);
            throw new BusinessException(AuthErrorCode.KAKAO_ERROR);
        }
    }
}
