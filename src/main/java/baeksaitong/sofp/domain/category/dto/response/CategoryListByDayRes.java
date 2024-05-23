package baeksaitong.sofp.domain.category.dto.response;

import java.util.List;

public record CategoryListByDayRes(
    List<CategoryDayDto> categoryList
) {
}
