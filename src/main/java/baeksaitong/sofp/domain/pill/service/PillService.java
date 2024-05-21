package baeksaitong.sofp.domain.pill.service;

import baeksaitong.sofp.domain.pill.dto.request.PillReq;
import baeksaitong.sofp.domain.pill.dto.response.PillInfoDTO;
import baeksaitong.sofp.domain.pill.dto.response.PillRes;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.pill.repository.ProfilePillRepository;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Pill;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.ProfilePill;
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
    private final ProfileRepository profileRepository;
    private final ProfilePillRepository profilePillRepository;
    private final PillRepository pillRepository;

    public PillRes getPillList(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        return getPillRes(profile);
    }

    public PillRes addPill(PillReq req, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

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

        return getPillRes(profile);
    }

    public PillRes removePill(PillReq req, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        List<Long> pillSerailNumberList = req.getPillSerailNumberList();
        List<Pill> pillList = pillRepository.findAllBySerialNumberIn(pillSerailNumberList);

        profilePillRepository.deleteAllByProfileAndPillIn(profile,pillList);

        return getPillRes(profile);
    }

    private PillRes getPillRes(Profile profile) {
        List<PillInfoDTO> pillInfoList = profilePillRepository.findAllByProfile(profile)
                .stream()
                .map(profilePill -> new PillInfoDTO(profilePill.getPill()))
                .collect(Collectors.toList());

        return new PillRes(pillInfoList);
    }
}
