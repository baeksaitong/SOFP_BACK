package baeksaitong.sofp.domain.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public record CategoryListByDayRes(
        @Schema(description = "카테고리 정보 리스트")
        Map<ProfileBasicDto, List<CategoryDayDto>> categoryList
) {
}
