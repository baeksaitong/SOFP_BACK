package baeksaitong.sofp.domain.category.dto.response;

import java.util.List;

public record CategoryListByProfileRes(
        List<CategoryProfileDto> categoryList
) {
}
