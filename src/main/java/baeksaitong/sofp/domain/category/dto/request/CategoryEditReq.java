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
public class CategoryEditReq {
    private String name;
    @ValidEnum(enumClass = Day.class, ignoreCase=true)
    private List<String> addIntakeDayList;
    @ValidEnum(enumClass = Day.class, ignoreCase=true)
    private List<String> deleteIntakeDayList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private List<LocalTime> addintakeTimeList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private List<LocalTime> deleteIntakeTimeList;
    private LocalDate period;
    private Boolean alarm;
}
