package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.enums.Day;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CategoryDetailRes(
        Long id,
        String name,
        Boolean alarm,
        LocalDate period,
        List<Day> intakeDayList,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        List<LocalTime> intakeTimeList
){
    public CategoryDetailRes(Category category, List<Day> intakeDayList, List<LocalTime> intakeTimeList){
        this(category.getId(), category.getName(), category.getAlarm(), category.getPeriod(), intakeDayList, intakeTimeList);
    }
}
