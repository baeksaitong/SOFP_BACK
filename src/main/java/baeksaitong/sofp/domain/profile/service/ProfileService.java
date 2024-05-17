package baeksaitong.sofp.domain.profile.service;

import baeksaitong.sofp.domain.profile.dto.request.ProfileImgEditReq;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.DiseaseAllergyRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileDiseaseAllergyRepository;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileDiseaseAllergyRepository profileDiseaseAllergyRepository;
    private final AwsS3Service awsS3Service;

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

    @Transactional
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
        profileRepository.save(profile);

        return new ProfileDetailRes(profile);
    }

    @Transactional
    public void setProfileImg(ProfileImgEditReq req, Member member) {
        Profile profile = profileRepository.findByNameAndMember(req.getName(), member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        if(profile.getImgUrl() != null){
            awsS3Service.deleteImage(profile.getImgUrl());
        }

        String imgUrl = awsS3Service.upload(req.getProfileImg(), profile.getId());
        profile.setImgUrl(imgUrl);
        profileRepository.save(profile);
    }
}
