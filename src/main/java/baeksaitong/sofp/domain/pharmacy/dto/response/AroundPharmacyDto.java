package baeksaitong.sofp.domain.pharmacy.dto.response;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacyInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AroundPharmacyDto (
        @Schema(description = "약국 ID")
        String pharmacyId,
        @Schema(description = "이름")
        String name,
        @Schema(description = "전화 번호")
        String tel,
        @Schema(description = "주소")
        String address,
        @Schema(description = "영업 시작 시간")
        LocalTime startTime,
        @Schema(description = "영업 마감 시간")
        LocalTime endTime,
        @Schema(description = "거리")
        double distance,
        @Schema(description = "위도")
        double latitude,
        @Schema(description = "경도")
        double longitude
){

    public AroundPharmacyDto(AroundPharmacyInfo info){
        this(
                info.getHpid(),
                info.getDutyName(),
                info.getDutyTel1(),
                info.getDutyAddr(),
                parseTime(info.getStartTime()),
                parseTime(info.getEndTime()),
                info.getDistance(),
                info.getLatitude(),
                info.getLongitude()
        );
    }

    private static LocalTime parseTime(String time) {
        if (time == null || time.isBlank()) {
            return null;
        }
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(2, 4));
        if (hour >= 24) {
            hour = hour - 24;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(String.format("%02d%02d", hour, minute), formatter);
    }
}
