package baeksaitong.sofp.domain.pharmacy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public record AroundPharmacyRes(
        @Schema(description = "약국 정보 리스트")
        List<AroundPharmacyDto> aroundPharmacyList
) {
}
