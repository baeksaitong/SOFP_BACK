package baeksaitong.sofp.domain.pill.service;

import baeksaitong.sofp.domain.category.dto.response.CategoryDetailRes;
import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.error.CategoryErrorCode;
import baeksaitong.sofp.domain.category.repository.CategoryRepository;
import baeksaitong.sofp.domain.category.service.CategoryService;
import baeksaitong.sofp.domain.pill.dto.request.PillReq;
import baeksaitong.sofp.domain.pill.dto.response.PillCategoryRes;
import baeksaitong.sofp.domain.pill.dto.response.PillInfoDTO;
import baeksaitong.sofp.domain.pill.dto.response.PillRes;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.pill.repository.ProfilePillRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.pill.entity.ProfilePill;
import baeksaitong.sofp.global.error.exception.BusinessException;
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
    private final CategoryService categoryService;

    public PillRes getPillList(Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        List<ProfilePill> profilePillList = profilePillRepository.findAllByProfile(profile);
        return new PillRes(getPillRes(profilePillList));
    }

    public PillRes addPill(PillReq req, Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        List<Long> pillSerailNumberList = req.getPillSerailNumberList();
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

        List<ProfilePill> profilePillList = profilePillRepository.findAllByProfile(profile);
        return new PillRes(getPillRes(profilePillList));
    }

    public PillRes removePill(PillReq req, Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        List<Long> pillSerailNumberList = req.getPillSerailNumberList();
        List<Pill> pillList = pillRepository.findAllBySerialNumberIn(pillSerailNumberList);

        profilePillRepository.deleteAllByProfileAndPillIn(profile,pillList);
        List<ProfilePill> profilePillList = profilePillRepository.findAllByProfile(profile);

        return new PillRes(getPillRes(profilePillList));
    }

    private List<PillInfoDTO> getPillRes(List<ProfilePill> profilePillList) {
        return profilePillList
                .stream()
                .map(profilePill -> new PillInfoDTO(profilePill.getPill()))
                .collect(Collectors.toList());
    }

    public PillCategoryRes getPillListByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(CategoryErrorCode.NO_SUCH_CATEGORY));

        List<ProfilePill> profilePillList = profilePillRepository.findAllByCategory(category);
        List<PillInfoDTO> pillInfoDTOList = getPillRes(profilePillList);
        CategoryDetailRes categoryDetailRes = categoryService.getCategoryDetailRes(category);

        return new PillCategoryRes(categoryDetailRes, pillInfoDTOList);
    }
}
