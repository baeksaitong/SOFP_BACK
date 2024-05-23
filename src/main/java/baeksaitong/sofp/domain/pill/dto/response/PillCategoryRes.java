package baeksaitong.sofp.domain.pill.dto.response;

import baeksaitong.sofp.domain.category.dto.response.CategoryDetailRes;

import java.util.List;

public record PillCategoryRes(
        CategoryDetailRes categoryInfo,
        List<PillInfoDTO> pillInfoList
) {
}
