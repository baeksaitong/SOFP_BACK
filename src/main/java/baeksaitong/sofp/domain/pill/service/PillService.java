package baeksaitong.sofp.domain.pill.service;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.error.CategoryErrorCode;
import baeksaitong.sofp.domain.category.repository.CategoryRepository;
import baeksaitong.sofp.domain.pill.dto.request.MovePillReq;
import baeksaitong.sofp.domain.pill.dto.request.PillReq;
import baeksaitong.sofp.domain.pill.dto.response.PillInfoDTO;
import baeksaitong.sofp.domain.pill.dto.response.PillRes;
import baeksaitong.sofp.domain.pill.error.PillErrorCode;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.pill.repository.ProfilePillRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.pill.entity.ProfilePill;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PillService {
    private final ProfileService profileService;
    private final ProfilePillRepository profilePillRepository;
    private final PillRepository pillRepository;
    private final CategoryRepository categoryRepository;

    public PillRes getPillList(String encryptedProfileId, String encryptedCategoryId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);
        Long categoryId = EncryptionUtil.decrypt(encryptedCategoryId);

        List<ProfilePill> profilePillList;

        if(categoryId == null && profileId != null) {
            Profile profile = profileService.getProfile(profileId);
            profilePillList = profilePillRepository.findAllByProfileAndCategoryIsNull(profile);
        }else if(categoryId != null && profileId == null){
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(CategoryErrorCode.NO_SUCH_CATEGORY));
            profilePillList = profilePillRepository.findAllByCategory(category);
        }else{
            throw new BusinessException(PillErrorCode.NEED_PROFILE_OR_CATEGORY);
        }

        return new PillRes(getPillRes(profilePillList));
    }

    public void addPill(PillReq req, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = profileService.getProfile(profileId);

        List<Long> pillSerailNumberList = req.getPillSeriallNumberList();
        List<Pill> pillList = pillRepository.findAllBySerialNumberIn(pillSerailNumberList);

        Set<Pill> exitsPillList = profilePillRepository.findAllByProfile(profile)
                .stream()
                .map(ProfilePill::getPill)
                .collect(Collectors.toSet());

        List<ProfilePill> addProfilePillList = new ArrayList<>();
        for (Pill pill : pillList) {

            if(exitsPillList.contains(pill)){
                continue;
            }

            ProfilePill profilePill = ProfilePill.builder()
                    .pill(pill)
                    .profile(profile)
                    .build();

            addProfilePillList.add(profilePill);
        }

        profilePillRepository.saveAll(addProfilePillList);
    }

    public void removePill(Long pillSerialNumber, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = profileService.getProfile(profileId);

        Pill pill = pillRepository.findBySerialNumber(pillSerialNumber)
                        .orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));

        profilePillRepository.deleteByProfileAndPill(profile, pill);
    }

    private List<PillInfoDTO> getPillRes(List<ProfilePill> profilePillList) {
        return profilePillList
                .stream()
                .map(profilePill -> new PillInfoDTO(profilePill.getPill()))
                .collect(Collectors.toList());
    }

    public void movePill(MovePillReq req, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillRepository.findBySerialNumber(req.getPillSerialNumber()).orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));
        ProfilePill profilePill = profilePillRepository.findByPillAndProfile(pill, profile).orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PROFILE_PILL));
        Category category = categoryRepository.findById(req.getDecryptCategoryId()).orElseThrow(() -> new BusinessException(CategoryErrorCode.NO_SUCH_CATEGORY));

        profilePill.setCategory(category);
        profilePillRepository.save(profilePill);
    }
}
