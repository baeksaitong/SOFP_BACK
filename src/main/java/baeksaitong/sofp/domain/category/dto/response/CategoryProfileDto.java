package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryProfileDto(
        @Schema(description = "카테고리 ID")
        Long categoryId,
        @Schema(description = "카테고리 이름")
        String name
) {
    public CategoryProfileDto(Category category){
        this(category.getId(), category.getName());
    }
}
