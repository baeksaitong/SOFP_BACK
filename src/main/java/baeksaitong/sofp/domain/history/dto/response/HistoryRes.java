package baeksaitong.sofp.domain.history.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HistoryRes(
        @Schema(description = "알약 리스트")
        List<HistoryDto> result
) {
}
