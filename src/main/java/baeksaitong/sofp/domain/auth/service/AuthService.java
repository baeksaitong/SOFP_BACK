package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.request.CheckIdReq;
import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static baeksaitong.sofp.global.common.entity.enums.MemberRole.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void singUp(SignUpReq req) {
        if(!memberRepository.existsByUid(req.getEmail())){
            throw new BusinessException(AuthErrorCode.INAVAILABLE_ID);
        }

        Member member = Member.builder()
                .name(req.getName())
                .uid(req.getEmail())
                .birthday(req.getBirthday())
                .pwd(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhone())
                .advertisement(req.getAdvertisement())
                .role(ROLE_USER)
                .build();

        memberRepository.save(member);
    }

    public void checkId(CheckIdReq req) {
        if(!memberRepository.existsByUid(req.getEmail())){
            throw new BusinessException(AuthErrorCode.INAVAILABLE_ID);
        }
    }
}
