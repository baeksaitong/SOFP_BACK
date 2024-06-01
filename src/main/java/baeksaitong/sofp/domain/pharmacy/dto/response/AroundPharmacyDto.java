package baeksaitong.sofp.domain.pharmacy.dto.response;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.AroundPharmacyInfo;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class AroundPharmacyDto {
    private String pharmacyId;
    private String name;
    private String tel;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private double distance;
    private double latitude;
    private double longitude;

    public AroundPharmacyDto(AroundPharmacyInfo info){
        this.pharmacyId = info.getHpid();
        this.name = info.getDutyName();
        this.tel = info.getDutyTel1();
        this.address = info.getDutyAddr();
        this.startTime = parseTime(info.getStartTime());
        this.endTime = parseTime(info.getEndTime());
        this.distance = info.getDistance();
        this.latitude = info.getLatitude();
        this.longitude = info.getLongitude();
    }

    private LocalTime parseTime(String time){
        if ("2400".equals(time)) {
            return LocalTime.MIDNIGHT;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(time, formatter);
    }
}
