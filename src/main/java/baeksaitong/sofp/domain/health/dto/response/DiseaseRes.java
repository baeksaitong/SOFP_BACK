package baeksaitong.sofp.domain.health.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DiseaseRes(
        @Schema(description = "질병 전체 리스트")
        List<String> diseaseRes
) {
}
