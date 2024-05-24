package baeksaitong.sofp.domain.category.service;

import baeksaitong.sofp.domain.calendar.repository.CalendarRepository;
import baeksaitong.sofp.domain.category.dto.request.CategoryEditReq;
import baeksaitong.sofp.domain.category.dto.request.CategoryReq;
import baeksaitong.sofp.domain.category.dto.response.*;
import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.IntakeDay;
import baeksaitong.sofp.domain.category.entity.IntakeTime;
import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.domain.category.error.CategoryErrorCode;
import baeksaitong.sofp.domain.category.repository.CategoryRepository;
import baeksaitong.sofp.domain.category.repository.IntakeDayRepository;
import baeksaitong.sofp.domain.category.repository.IntakeTimeRepository;
import baeksaitong.sofp.domain.pill.entity.ProfilePill;
import baeksaitong.sofp.domain.pill.repository.ProfilePillRepository;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final IntakeTimeRepository intakeTimeRepository;
    private final IntakeDayRepository intakeDayRepository;
    private final ProfilePillRepository profilePillRepository;
    private final ProfileService profileService;
    private final CalendarRepository calendarRepository;


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

    public CategoryDetailRes getCategoryInfo(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(CategoryErrorCode.NO_SUCH_CATEGORY));
        return getCategoryDetailRes(category);
    }

    public CategoryDetailRes getCategoryDetailRes(Category category) {
        List<Day> days = intakeDayRepository.findAllByCategory(category)
                .stream()
                .map(IntakeDay::getDay)
                .toList();
        List<LocalTime> times = intakeTimeRepository.findAllByCategory(category)
                .stream()
                .map(IntakeTime::getTime)
                .toList();

        return new CategoryDetailRes(category, days, times);
    }

    public CategoryListByProfileRes getCategoryListByProfile(Long profileId) {
        Profile profile = profileService.getProfile(profileId);
        List<CategoryProfileDto> categoryList = categoryRepository.findAllByProfile(profile)
                .stream()
                .map(CategoryProfileDto::new)
                .toList();

        return new CategoryListByProfileRes(categoryList);
    }

    public CategoryListByDayRes getCategoryListByDay(Long profileId, String day) {
        Profile profile = profileService.getProfile(profileId);
        List<Profile> targetProfileList = calendarRepository.findTargetProfilesByOwnerProfile(profile);
        targetProfileList.add(profile);

        List<IntakeDay> intakeDays = intakeDayRepository.findAllByDayAndProfileIn(Day.from(day), targetProfileList);

        List<Category> categoryList = intakeDays.stream()
                .map(IntakeDay::getCategory)
                .distinct()
                .toList();
        List<IntakeTime> intakeTimes = intakeTimeRepository.findAllByCategoryIn(categoryList);

        Map<Category, List<IntakeTime>> intakeTimeMap = intakeTimes.stream()
                .collect(Collectors.groupingBy(IntakeTime::getCategory));

        List<CategoryDayDto> categoryDayDtoList = categoryList.stream()
                .map(category -> new CategoryDayDto(
                        category.getId(),
                        category.getProfile().getColor(),
                        category.getName(),
                        intakeTimeMap.getOrDefault(category, List.of()).stream().map(IntakeTime::getTime).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new CategoryListByDayRes(categoryDayDtoList);
    }

    public CategoryDetailRes editCategory(Long categoryId, CategoryEditReq req) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(CategoryErrorCode.NO_SUCH_CATEGORY));
        Profile profile = category.getProfile();

        if(!req.getName().equals(category.getName()) && categoryRepository.existsByNameAndProfile(req.getName(), profile)){
            throw new BusinessException(CategoryErrorCode.DUPLICATE_CATEGORY_NAME);
        }

        category.edit(req.getName(), req.getAlarm(), req.getPeriod());
        categoryRepository.save(category);


        List<Day> deleteDayList = getDayList(req.getDeleteIntakeDayList());
        intakeDayRepository.deleteAllByCategoryAndDayIn(category, deleteDayList);

        List<LocalTime> deleteTimeList = getTimeList(req.getDeleteIntakeTimeList());
        intakeTimeRepository.deleteAllByCategoryAndTimeIn(category, deleteTimeList);


        List<LocalTime> addTimeList = getTimeList(req.getAddintakeTimeList());
        List<IntakeTime> addIntakeTimeList = addTimeList.stream()
                .map(time -> IntakeTime.builder()
                        .time(time)
                        .category(category)
                        .build())
                .collect(Collectors.toList());
        intakeTimeRepository.saveAll(addIntakeTimeList);


        List<IntakeDay> addIntakeDayList = getDayList(req.getAddIntakeDayList())
                .stream()
                .map(day -> IntakeDay.builder()
                        .day(day)
                        .category(category)
                        .profile(profile)
                        .build()
                ).toList();
        intakeDayRepository.saveAll(addIntakeDayList);

        return getCategoryDetailRes(category);
    }

    private List<Day> getDayList(List<String> stringList) {
        return Optional.ofNullable(stringList)
                .orElse(Collections.emptyList())
                .stream()
                .map(Day::from)
                .collect(Collectors.toList());
    }

    private List<LocalTime> getTimeList(List<LocalTime> timeList) {
        return Optional.ofNullable(timeList)
                .orElse(Collections.emptyList());
    }

    public void deleteCategory(Long categoryId, Boolean isAllDelete) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BusinessException(CategoryErrorCode.NO_SUCH_CATEGORY));
        intakeTimeRepository.deleteAllByCategory(category);
        intakeDayRepository.deleteAllByCategory(category);

        List<ProfilePill> profilePillList = profilePillRepository.findAllByCategory(category);

        if(isAllDelete){
            profilePillRepository.deleteAll(profilePillList);
        }else{
            List<ProfilePill> editProfilePillList = profilePillList.stream()
                    .peek(profilePill -> profilePill.setCategory(null))
                    .toList();
            profilePillRepository.saveAll(editProfilePillList);
        }

        categoryRepository.delete(category);
    }

}
