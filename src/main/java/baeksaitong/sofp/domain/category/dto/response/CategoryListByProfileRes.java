package baeksaitong.sofp.domain.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoryListByProfileRes(
        @Schema(description = "카테고리 기본 정보 리스트")
        List<CategoryProfileDto> categoryList
) {
}
