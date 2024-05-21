package baeksaitong.sofp.domain.profile.service;

import baeksaitong.sofp.domain.profile.dto.request.ProfileImgEditReq;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final AwsS3Service awsS3Service;

    public void addProfile(ProfileReq req, Member member){
        if(profileRepository.countByMember(member) > 3){
            throw new BusinessException(ProfileErrorCode.EXCEEDED_PROFILE_LIMIT);
        }

        Profile profile = Profile.builder()
                .member(member)
                .name(req.getName())
                .birthday(req.getBirthday())
                .gender(req.getGender())
                .color(req.getColor())
                .build();

        profileRepository.save(profile);
    }

    public ProfileBasicRes getProfileBasic(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));
        return new ProfileBasicRes(profile);
    }

    public ProfileDetailRes getProfileDetail(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));
        return new ProfileDetailRes(profile);
    }

    public void deleteProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        profileRepository.delete(profile);
    }

    public ProfileDetailRes editProfile(ProfileReq req, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        profile.edit(req.getName(), req.getBirthday(), req.getGender(), req.getColor());
        profileRepository.save(profile);

        return new ProfileDetailRes(profile);
    }

    public void setProfileImg( ProfileImgEditReq req, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        if(profile.getImgUrl() != null){
            awsS3Service.deleteImage(profile.getImgUrl());
        }

        String imgUrl = awsS3Service.upload(req.getProfileImg(), profile.getId());
        profile.setImgUrl(imgUrl);
        profileRepository.save(profile);
    }

}
