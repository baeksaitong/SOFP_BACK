package baeksaitong.sofp.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PillInfoRes(
        @Schema(description = "알약 시리얼 번호")
        Long serialNumber,
        @Schema(description = "알약 이름")
        String name
) {
}
