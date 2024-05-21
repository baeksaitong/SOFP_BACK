package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.member.dto.request.MemberEditReq;
import baeksaitong.sofp.domain.member.dto.response.MemberRes;
import baeksaitong.sofp.domain.member.dto.response.ProfileListRes;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.domain.member.entity.Member;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public MemberRes editMember(MemberEditReq req, Member member) {
        if(req.getPassword() != null) member.setPassword(passwordEncoder.encode(req.getPassword()));
        member.setAdvertisement(req.getAdvertisement());

        memberRepository.save(member);

        return new MemberRes(member);
    }

    public void verification(String password, Member member) {
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BusinessException(AuthErrorCode.WRONG_PASSWORD);
        }
    }

    public MemberRes getMemberInfo(Member member) {
        return new MemberRes(member);
    }

    public ProfileListRes getProfileList(Member member) {
        List<ProfileBasicRes> res = profileRepository.findAllByMember(member)
                .stream()
                .map(ProfileBasicRes::new)
                .collect(Collectors.toList());
        return new ProfileListRes(res);
    }
}
