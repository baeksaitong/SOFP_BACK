package baeksaitong.sofp.domain.pill.dto.response;

import baeksaitong.sofp.domain.category.dto.response.CategoryProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PillMainRes(
        List<CategoryProfileDto> categoryList,
        @Schema(description = "사용자가 현재 복용중인 알약 정보 리스트")
        List<PillInfoDTO> pillInfoList
) {

}
