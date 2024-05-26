package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.category.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.List;

public record CategoryDayDto(
        @Schema(description = "카테고리 ID")
        String categoryId,
        @Schema(description = "프로필 색상")
        String color,
        @Schema(description = "카테고리 이름")
        String name,
        @Schema(description = "섭취 시간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        List<LocalTime> intakeTime
) {
        public CategoryDayDto(Category category, List<LocalTime> intakeTime){
                this(category.getEncryptedId(), category.getProfile().getColor(), category.getName(), intakeTime);
        }
}
