package baeksaitong.sofp.domain.favorite.dto.response;

import baeksaitong.sofp.domain.pill.entity.Pill;
import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteDto(
        @Schema(description = "알약 고유 번호")
        Long serialNumber,
        @Schema(description = "알약 이름")
        String name,
        @Schema(description = "알약 분류")
        String classification,
        @Schema(description = "알약 제조 회사")
        String enterprise,
        @Schema(description = "알약 성상")
        String chart,
        @Schema(description = "알약 이미지 URL")
        String imgUrl) {

        public FavoriteDto(Pill pill, String imgUrl){
                this(pill.getSerialNumber(), pill.getName(), pill.getClassification(), pill.getEnterprise(), pill.getChart(), imgUrl);
        }
}