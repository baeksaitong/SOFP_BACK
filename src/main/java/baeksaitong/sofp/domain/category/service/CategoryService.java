package baeksaitong.sofp.domain.category.service;

import baeksaitong.sofp.domain.category.dto.request.CategoryReq;
import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.IntakeDay;
import baeksaitong.sofp.domain.category.entity.IntakeTime;
import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.domain.category.error.CategoryErrorCode;
import baeksaitong.sofp.domain.category.repository.CategoryRepository;
import baeksaitong.sofp.domain.category.repository.IntakeDayRepository;
import baeksaitong.sofp.domain.category.repository.IntakeTimeRepository;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final IntakeTimeRepository intakeTimeRepository;
    private final IntakeDayRepository intakeDayRepository;
    private final ProfileService profileService;


    public void addCategory(CategoryReq req, Long profileId) {
        Profile profile = profileService.getProfile(profileId);
        if(categoryRepository.existsByNameAndProfile(req.getName(), profile)){
            throw new BusinessException(CategoryErrorCode.DUPLICATE_CATEGORY_NAME);
        }

        Category category = Category.builder()
                .name(req.getName())
                .period(req.getPeriod())
                .alarm(req.getAlarm())
                .profile(profile)
                .build();

        categoryRepository.save(category);

        List<IntakeDay> intakeDayList = req.getIntakeDayList()
                .stream()
                .map(day -> IntakeDay.builder()
                        .day(Day.from(day))
                        .profile(profile)
                        .category(category)
                        .build()
                ).toList();

        intakeDayRepository.saveAll(intakeDayList);

        List<IntakeTime> intakeTimeList = req.getIntakeTimeList()
                .stream()
                .map(time -> IntakeTime.builder()
                        .category(category)
                        .time(time)
                        .build()
                ).toList();

        intakeTimeRepository.saveAll(intakeTimeList);

    }
}
