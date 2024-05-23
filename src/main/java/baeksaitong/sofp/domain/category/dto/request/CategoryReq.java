package baeksaitong.sofp.domain.category.dto.request;

import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryReq {
    private String name;
    @ValidEnum(enumClass = Day.class, ignoreCase=true)
    private List<String> intakeDayList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private List<LocalTime> intakeTimeList;
    private LocalDate period;
    private Boolean alarm;
}
