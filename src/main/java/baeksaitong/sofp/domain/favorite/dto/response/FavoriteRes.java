package baeksaitong.sofp.domain.favorite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteRes(
        @Schema(description = "알약 고유 번호")
        Long serialNumber,
        @Schema(description = "알약 이름")
        String name,
        @Schema(description = "알약 분류")
        String classification,
        @Schema(description = "알약 제조 회사")
        String enterprise,
        @Schema(description = "알약 서상")
        String chart,
        @Schema(description = "알약 이미지 URL")
        String imgUrl) {
}