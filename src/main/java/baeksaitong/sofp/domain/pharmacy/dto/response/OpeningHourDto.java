package baeksaitong.sofp.domain.pharmacy.dto.response;

import java.time.LocalTime;

public record OpeningHourDto (
    LocalTime star,
    LocalTime end
){
}
