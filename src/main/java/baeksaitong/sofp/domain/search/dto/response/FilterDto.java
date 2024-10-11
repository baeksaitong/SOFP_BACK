package baeksaitong.sofp.domain.search.dto.response;

import baeksaitong.sofp.domain.pill.entity.Pill;
import io.swagger.v3.oas.annotations.media.Schema;

public record FilterDto(
        @Schema(description = "모양")
        String shape,
        @Schema(description = "앞면 기호")
        String signFront,
        @Schema(description = "뒷면 기호")
        String signBack,
        @Schema(description = "앞면 분할선")
        String lineFront,
        @Schema(description = "뒷면 분할선")
        String lineBack,
        @Schema(description = "앞면 색상")
        String colorFront,
        @Schema(description = "뒷면 색상")
        String colorBack
) {
    public FilterDto(Pill pill) {
        this(pill.getShape(),
                pill.getSignFront(),
                pill.getSignBack(),
                pill.getLineFront(),
                pill.getLineBack(),
                pill.getColorFront(),
                pill.getColorBack());
    }
}
