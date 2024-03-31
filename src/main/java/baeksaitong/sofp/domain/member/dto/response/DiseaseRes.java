package baeksaitong.sofp.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DiseaseRes(
        @Schema(description = "사용자 질병 정보")
        List<String> DiseaseRes
) {
}
