package baeksaitong.sofp.domain.history.dto.response;

import baeksaitong.sofp.domain.pill.entity.Pill;
import io.swagger.v3.oas.annotations.media.Schema;

public record HistoryDto(
        @Schema(description = "알약 일련 번호")
        Long serialNumber,
        @Schema(description = "이름")
        String name,
        @Schema(description = "분류")
        String classification,
        @Schema(description = "이미지")
        String imgUrl,
        @Schema(description = "성상")
        String chart,
        @Schema(description = "제조 업체")
        String enterprise
) {
        public HistoryDto(Pill pill){
                this(pill.getSerialNumber(), pill.getName(), pill.getClassification(), pill.getImgUrl(), pill.getChart(), pill.getEnterprise());
        }
}
