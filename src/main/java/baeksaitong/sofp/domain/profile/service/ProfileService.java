package baeksaitong.sofp.domain.profile.service;

import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public void addProfile(ProfileReq req, Member member){
        Profile profile = Profile.builder()
                .member(member)
                .name(req.getName())
                .birthday(req.getBirthday())
                .gender(MemberGender.from(req.getGender()))
                .build();

        profileRepository.save(profile);
    }
}
