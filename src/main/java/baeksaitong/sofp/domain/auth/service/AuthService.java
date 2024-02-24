package baeksaitong.sofp.domain.auth.service;

import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.global.common.entity.Member;
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
}
