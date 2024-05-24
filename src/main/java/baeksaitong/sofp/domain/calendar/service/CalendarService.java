package baeksaitong.sofp.domain.calendar.service;

import baeksaitong.sofp.domain.calendar.dto.response.TargetRes;
import baeksaitong.sofp.domain.calendar.repository.CalendarRepository;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final ProfileService profileService;

    public TargetRes getTarget(Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        if(!calendarRepository.existsByOwnerProfile(profile)){
            return new TargetRes(new ArrayList<>());
        }

        Set<Long> targetProfileIdList = calendarRepository.findTargetProfilesByOwnerProfile(profile)
                .stream().map(Profile::getId).collect(Collectors.toSet());
        targetProfileIdList.add(profileId);

        return new TargetRes(targetProfileIdList.stream().toList());
    }
}
