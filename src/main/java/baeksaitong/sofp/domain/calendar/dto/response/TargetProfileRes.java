package baeksaitong.sofp.domain.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record TargetProfileRes(
        @Schema(description = "달력에 표시되는 프로필 ID 리스트")
        List<Long> targetProfileIdList
) {
}
