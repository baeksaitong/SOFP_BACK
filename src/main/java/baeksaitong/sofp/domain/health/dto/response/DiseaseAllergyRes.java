package baeksaitong.sofp.domain.health.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DiseaseAllergyRes(
        @Schema(description = "질병 및 알레르기 명칭 리스트")
        List<String> DiseaseAllergyList
) {
}
