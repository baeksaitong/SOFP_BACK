package baeksaitong.sofp.domain.pharmacy.dto.response;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacyInfo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AroundPharmacyDto (
    String pharmacyId,
    String name,
    String tel,
    String address,
    LocalTime startTime,
    LocalTime endTime,
    double distance,
    double latitude,
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

    private static LocalTime parseTime(String time){
        if ("2400".equals(time)) {
            return LocalTime.MIDNIGHT;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(time, formatter);
    }
}
