package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.request.CheckIdReq;
import baeksaitong.sofp.domain.auth.dto.request.LoginReq;
import baeksaitong.sofp.domain.auth.dto.request.RefreshTokenReq;
import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.dto.response.RefreshAccessRes;
import baeksaitong.sofp.domain.auth.dto.response.TokenRes;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static baeksaitong.sofp.global.common.entity.enums.MemberRole.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;

    public void checkId(CheckIdReq req) {
        if(memberRepository.existsByEmail(req.getEmail())){
            throw new BusinessException(AuthErrorCode.DUPLICATIE_ID);
        }
    }

    public LoginRes singUp(SignUpReq req) {
        if(memberRepository.existsByEmail(req.getEmail())){
            throw new BusinessException(AuthErrorCode.DUPLICATIE_ID);
        }

        Member member = Member.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .advertisement(req.getAdvertisement())
                .role(ROLE_USER)
                .build();

        Member save = memberRepository.save(member);

        profileService.addProfile(new ProfileReq(req.getName(), req.getBirthday(),req.getGender(),req.getColor()), save);

        return new LoginRes(
                true,
                login(LoginReq.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .build())
        );
    }

    public TokenRes login(LoginReq req) {
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new BusinessException(AuthErrorCode.NO_SUCH_ID)
        );

        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())){
            throw new BusinessException(AuthErrorCode.WRONG_PASSWORD);
        }

        Authentication authentication = createAuthentication(req);

        return new TokenRes(jwtTokenProvider.createAccessToken(authentication), jwtTokenProvider.createRefreshToken(authentication));
    }

    private Authentication createAuthentication(LoginReq req) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public LoginRes oauthLogin(
            String email, String id, LocalDate birthday, String name, MemberGender gender, Boolean agreement
    ){
        boolean isNew = false;

        if(!memberRepository.existsByEmail(email)){
            singUp(SignUpReq.builder()
                    .email(email)
                    .password(id)
                    .birthday(birthday)
                    .name(name)
                    .advertisement(agreement)
                    .gender(String.valueOf(gender))
                    .build());
            isNew = true;
        }

        return new LoginRes(isNew,
                login(LoginReq.builder()
                .email(email)
                .password(id)
                .build()));
    }

    public RefreshAccessRes refreshAccessToken0(RefreshTokenReq req) {
        Authentication authentication = validateRefreshTokenAndGetAuthentication(req);
        return new RefreshAccessRes(jwtTokenProvider.createAccessToken(authentication));
    }


    public String getExpiration(String token) {
        jwtTokenProvider.validateToken(token);
        Date expiration = jwtTokenProvider.getExpiration(token);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(expiration);
    }

    public TokenRes refresh(RefreshTokenReq req) {
        Authentication authentication = validateRefreshTokenAndGetAuthentication(req);
        return new TokenRes(jwtTokenProvider.createAccessToken(authentication), jwtTokenProvider.createRefreshToken(authentication) );
    }

    private Authentication validateRefreshTokenAndGetAuthentication(RefreshTokenReq req) {
        String token = req.refreshToken();
        String userId = jwtTokenProvider.getUserId(token);
        jwtTokenProvider.validateRefreshToken(token, userId);
        return jwtTokenProvider.getAuthentication(token);
    }
}
