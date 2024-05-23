package baeksaitong.sofp.domain.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoryListByDayRes(
        @Schema(description = "카테고리 정보 리스트")
        List<CategoryDayDto> categoryList
) {
}
