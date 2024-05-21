package baeksaitong.sofp.domain.profile.service;

import baeksaitong.sofp.domain.profile.dto.response.ProfileListRes;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.domain.member.entity.Member;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.redis.constants.RedisPrefix;
import baeksaitong.sofp.global.redis.service.RedisService;
import baeksaitong.sofp.global.s3.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final AwsS3Service awsS3Service;
    private final RedisService redisService;

    public Profile getProfile(Long id){
        if(redisService.hasKey(RedisPrefix.PROFILE, String.valueOf(id))){
            return (Profile) redisService.get(RedisPrefix.PROFILE, String.valueOf(id));
        }

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        profile.initialize();

        redisService.save(RedisPrefix.PROFILE, String.valueOf(id), profile, RedisPrefix.PROFILE.getDuration());

        return profile;
    }

    private void deleteProfileCache(Long id){
        if(redisService.hasKey(RedisPrefix.PROFILE, String.valueOf(id))){
            redisService.delete(RedisPrefix.PROFILE, String.valueOf(id));
        }
    }

    public ProfileListRes getProfileList(Member member) {
        List<ProfileBasicRes> res = profileRepository.findAllByMember(member)
                .stream()
                .map(ProfileBasicRes::new)
                .collect(Collectors.toList());
        return new ProfileListRes(res);
    }

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
        Profile profile = getProfile(profileId);
        return new ProfileBasicRes(profile);
    }

    public ProfileDetailRes getProfileDetail(Long profileId) {
        Profile profile = getProfile(profileId);
        return new ProfileDetailRes(profile);
    }

    public void deleteProfile(Long profileId) {
        Profile profile = getProfile(profileId);

        profileRepository.delete(profile);
        deleteProfileCache(profileId);
    }

    public ProfileDetailRes editProfile(ProfileReq req, Long profileId) {
        Profile profile = getProfile(profileId);

        profile.edit(req.getName(), req.getBirthday(), req.getGender(), req.getColor());

        if(profile.getImgUrl() != null){
            setProfileImg(profile, req.getProfileImg());
        }

        profileRepository.save(profile);

        deleteProfileCache(profileId);

        return new ProfileDetailRes(profile);
    }

    public void setProfileImg(Profile profile, MultipartFile profileImg) {
        if(profile.getImgUrl() != null){
            awsS3Service.deleteImage(profile.getImgUrl());
        }

        String imgUrl = awsS3Service.upload(profileImg, profile.getId());
        profile.setImgUrl(imgUrl);
    }

}
