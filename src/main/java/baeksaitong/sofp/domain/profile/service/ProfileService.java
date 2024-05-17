package baeksaitong.sofp.domain.profile.service;

import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public void addProfile(ProfileReq req, Member member){
        if(profileRepository.countByMember(member) > 4){
            throw new BusinessException(ProfileErrorCode.EXCEEDED_PROFILE_LIMIT);
        }

        Profile profile = Profile.builder()
                .member(member)
                .name(req.getName())
                .birthday(req.getBirthday())
                .gender(req.getGender())
                .build();

        profileRepository.save(profile);
    }

    public ProfileBasicRes getProfileBasic(String name, Member member) {
        Profile profile = profileRepository.findByNameAndMember(name, member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));
        return new ProfileBasicRes(profile);
    }

    public ProfileDetailRes getProfileDetail(String name, Member member) {
        Profile profile = profileRepository.findByNameAndMember(name, member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));
        return new ProfileDetailRes(profile);
    }

    @Transactional(readOnly = true)
    public void deleteProfile(String name, Member member) {
        Profile profile = profileRepository.findByNameAndMember(name, member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        profileRepository.delete(profile);
    }

    @Transactional
    public ProfileDetailRes editProfile(ProfileReq req, Member member) {
        Profile profile = profileRepository.findByNameAndMember(req.getName(), member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        profile.edit(profile.getName(), req.getBirthday(), req.getGender());

        return new ProfileDetailRes(profile);
    }
}
