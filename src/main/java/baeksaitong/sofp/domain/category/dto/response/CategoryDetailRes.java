package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.enums.Day;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CategoryDetailRes(
        @Schema(description = "카테고리 id")
        String id,
        @Schema(description = "카테고리 이름")
        String name,
        @Schema(description = "알람 허용 여부")
        Boolean alarm,
        @Schema(description = "섭취 기간(섭취 종료 날짜)")
        LocalDate period,
        @Schema(description = "섭취 요일")
        List<Day> intakeDayList,
        @Schema(description = "섭취 시간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        List<LocalTime> intakeTimeList
){
    public CategoryDetailRes(Category category, List<Day> intakeDayList, List<LocalTime> intakeTimeList){
        this(category.getEncryptedId(), category.getName(), category.getAlarm(), category.getPeriod(), intakeDayList, intakeTimeList);
    }
}
