package baeksaitong.sofp.domain.category.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.List;

public record CategoryDayDto(
        @Schema(description = "카테고리 ID")
        Long categoryId,
        @Schema(description = "프로필 색상")
        String color,
        @Schema(description = "카테고리 이름")
        String name,
        @Schema(description = "섭취 시간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        List<LocalTime> intakeTime
) {
}
