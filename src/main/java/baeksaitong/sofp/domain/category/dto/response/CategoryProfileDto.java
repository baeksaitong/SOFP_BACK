package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.category.entity.Category;

public record CategoryProfileDto(
        Long categoryId, String name
) {
    public CategoryProfileDto(Category category){
        this(category.getId(), category.getName());
    }
}
