package baeksaitong.sofp.domain.profile.service;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.repository.CategoryRepository;
import baeksaitong.sofp.domain.category.repository.IntakeDayRepository;
import baeksaitong.sofp.domain.category.repository.IntakeTimeRepository;
import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.health.repository.ProfileDiseaseAllergyRepository;
import baeksaitong.sofp.domain.history.repository.HistoryRepository;
import baeksaitong.sofp.domain.member.entity.Member;
import baeksaitong.sofp.domain.pill.repository.ProfilePillRepository;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileListRes;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.service.AwsS3Service;
import baeksaitong.sofp.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
    private final ProfilePillRepository profilePillRepository;
    private final ProfileDiseaseAllergyRepository profileDiseaseAllergyRepository;
    private final FavoriteRepository favoriteRepository;
    private final HistoryRepository historyRepository;
    private final CategoryRepository categoryRepository;
    private final IntakeTimeRepository intakeTimeRepository;
    private final IntakeDayRepository intakeDayRepository;
    private final AwsS3Service awsS3Service;

    public Profile getProfile(Long id){
        return profileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));
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

    public ProfileBasicRes getProfileBasic(String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = getProfile(profileId);
        return new ProfileBasicRes(profile);
    }

    public ProfileDetailRes getProfileDetail(String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = getProfile(profileId);
        return new ProfileDetailRes(profile);
    }

    public void deleteProfile(String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = getProfile(profileId);

        List<Category> categoryList = categoryRepository.findAllByProfile(profile);
        intakeDayRepository.deleteAllByCategoryIn(categoryList);
        intakeTimeRepository.deleteAllByCategoryIn(categoryList);

        profilePillRepository.deleteAllByProfile(profile);
        profileDiseaseAllergyRepository.deleteAllByProfile(profile);
        favoriteRepository.deleteAllByProfile(profile);
        historyRepository.deleteById(profileId);

        profileRepository.delete(profile);
    }

    public ProfileDetailRes editProfile(ProfileReq req, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = getProfile(profileId);

        Hibernate.initialize(profile.getMember());

        profile.edit(req.getName(), req.getBirthday(), req.getGender(), req.getColor());

        if(req.getProfileImg() != null){
            setProfileImg(profile, req.getProfileImg());
        }

        profileRepository.save(profile);

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
