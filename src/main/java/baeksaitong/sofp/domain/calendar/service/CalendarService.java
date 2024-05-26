package baeksaitong.sofp.domain.calendar.service;

import baeksaitong.sofp.domain.calendar.dto.request.EditTargetProfile;
import baeksaitong.sofp.domain.calendar.dto.response.TargetProfileRes;
import baeksaitong.sofp.domain.calendar.entity.Calendar;
import baeksaitong.sofp.domain.calendar.repository.CalendarRepository;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
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
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    public TargetProfileRes getTargetProfile(String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);
        Profile profile = profileService.getProfile(profileId);

        if(!calendarRepository.existsByOwnerProfile(profile)){
            return new TargetProfileRes(new ArrayList<>());
        }

        Set<String> targetProfileIdList = calendarRepository.findTargetProfilesByOwnerProfile(profile)
                .stream().map(Profile::getEncryptedId).collect(Collectors.toSet());
        targetProfileIdList.add(String.valueOf(encryptedProfileId));

        return new TargetProfileRes(targetProfileIdList.stream().toList());
    }

    public void editTargetProfile(EditTargetProfile req, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);
        Profile profile = profileService.getProfile(profileId);

        List<Long> addTargetProfileIdList = req.getDecryptAdd();
        List<Long> deleteTargetProfileIdList = req.getDecryptDelete();

        List<Profile> addProfileList = profileRepository.findAllByIdIn(addTargetProfileIdList);
        List<Profile> deleteProfileList = profileRepository.findAllByIdIn(deleteTargetProfileIdList);

        List<Calendar> deleteCalendarList = calendarRepository.findAllByOwnerProfileAndTargetProfileIn(profile, deleteProfileList);
        List<Calendar> addCalendarList = addProfileList.stream()
                .map(addProfile -> Calendar.builder()
                        .ownerProfile(profile)
                        .targetProfile(addProfile)
                        .build()
                ).toList();

        calendarRepository.deleteAll(deleteCalendarList);
        calendarRepository.saveAll(addCalendarList);
    }
}
