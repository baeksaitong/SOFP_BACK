package baeksaitong.sofp.domain.category.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import java.util.List;

public record CategoryDayDto(
        Long categoryId,
        String color,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        List<LocalTime> intakeTime
) {
}
