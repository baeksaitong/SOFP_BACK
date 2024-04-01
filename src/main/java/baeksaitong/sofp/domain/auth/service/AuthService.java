package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.request.CheckIdReq;
import baeksaitong.sofp.domain.auth.dto.request.LoginReq;
import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
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

import java.time.LocalDate;

import static baeksaitong.sofp.global.common.entity.enums.MemberRole.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginRes singUp(SignUpReq req) {
        if(memberRepository.existsByEmail(req.getEmail())){
            throw new BusinessException(AuthErrorCode.DUPLICATIE_ID);
        }

        Member member = Member.builder()
                .name(req.getName())
                .email(req.getEmail())
                .birthday(req.getBirthday())
                .pwd(passwordEncoder.encode(req.getPassword()))
                .gender(req.getGender())
                .advertisement(req.getAdvertisement())
                .role(ROLE_USER)
                .build();

        memberRepository.save(member);

        return new LoginRes(
                true,
                login(LoginReq.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .build())
        );
    }

    public void checkId(CheckIdReq req) {
        if(memberRepository.existsByEmail(req.getEmail())){
            throw new BusinessException(AuthErrorCode.DUPLICATIE_ID);
        }
    }

    public String login(LoginReq req) {
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new BusinessException(AuthErrorCode.NO_SUCH_ID)
        );

        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())){
            throw new BusinessException(AuthErrorCode.WRONG_PASSWORD);
        }

        return createAccessToken(req);
    }

    private String createAccessToken(LoginReq req) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.createAccessToken(authentication);
    }

    public LoginRes oauthLogin(
            String email, String id, LocalDate birthday, String name, MemberGender gender
    ){
        boolean isNew = false;

        if(!memberRepository.existsByEmail(email)){
            singUp(SignUpReq.builder()
                    .email(email)
                    .password(id)
                    .birthday(birthday)
                    .name(name)
                    .advertisement(true)
                    .gender(gender)
                    .build());
            isNew = true;
        }

        return new LoginRes(isNew,
                login(LoginReq.builder()
                .email(email)
                .password(id)
                .build()));
    }
}
