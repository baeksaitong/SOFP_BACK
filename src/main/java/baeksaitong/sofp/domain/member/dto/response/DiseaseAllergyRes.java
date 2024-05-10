package baeksaitong.sofp.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DiseaseAllergyRes(
        @Schema(description = "사용자 질병 및 알레르기 정보")
        List<String> DiseaseAllergyRes
) {
}
