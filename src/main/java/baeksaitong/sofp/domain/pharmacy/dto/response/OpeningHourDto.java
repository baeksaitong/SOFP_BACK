package baeksaitong.sofp.domain.pharmacy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public record OpeningHourDto (
        @Schema(description = "영업 시작 시간")
        LocalTime start,
        @Schema(description = "영업 마감 시간")
        LocalTime end
){
}
