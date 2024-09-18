package baeksaitong.sofp.domain.category.dto.request;

import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.global.common.validation.NonNullList;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryReq {
    @Schema(description = "카테고리 이름")
    @NotNull(message = "카테고리 이름이 필요합니다.")
    private String name;
    @Schema(description = "섭취 요일(없을 시 빈 배열 필요)", example = "MON,TUE,WED,THU,FRI,SAT,SUN")
    @NonNullList(message = "섭취 요일 리스트는 null 이거나 null 값을 포함할 수 없습니다.")
    @ValidEnum(enumClass = Day.class, ignoreCase=true, message = "잘못된 요일 입력입니다.")
    private List<String> intakeDayList = new ArrayList<>();
    @Schema(description = "섭취 시간(없을 시 빈 배열 필요)", example = "08:30 (HH:mm 형식)")
    @NonNullList(message = "섭취 시간 리스트는 null 이거나 null 값을 포함할 수 없습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private List<LocalTime> intakeTimeList = new ArrayList<>();
    @Schema(description = "섭취 기간 (섭취 종료 날짜)")
    private LocalDate period;
    @Schema(description = "알람 허용 여부")
    @NotNull(message = "알람 허용 여부 정보가 필요합니다.")
    private Boolean alarm;
}
