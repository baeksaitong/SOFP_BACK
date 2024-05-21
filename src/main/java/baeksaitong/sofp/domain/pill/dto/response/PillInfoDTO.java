package baeksaitong.sofp.domain.pill.dto.response;

import baeksaitong.sofp.global.common.entity.Pill;
import io.swagger.v3.oas.annotations.media.Schema;

public record PillInfoDTO(
        @Schema(description = "알약 시리얼 번호")
        Long serialNumber,
        @Schema(description = "알약 이름")
        String name,
         @Schema(description = "알약 분류")
        String classification,
        @Schema(description = "알약 이미지")
        String imgUrl,
        @Schema(description = "알약 성상")
        String chart,
        @Schema(description = "알약 제조 업체")
        String enterprise
) {
        public PillInfoDTO(Pill pill){
                this(pill.getSerialNumber(), pill.getName(), pill.getClassification(), pill.getImgUrl(), pill.getChart(), pill.getEnterprise() );
        }
}
