package baeksaitong.sofp.domain.pharmacy.dto.response;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.PharmacyInfo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record PharmacyRes (
        String address,
        String roadAddress,
        String phone,
        OpeningHourDto mon,
        OpeningHourDto tue,
        OpeningHourDto wed,
        OpeningHourDto thu,
        OpeningHourDto fri,
        OpeningHourDto sat,
        OpeningHourDto sun
) {
    public PharmacyRes(PharmacyInfo pharmacyInfo, String address) {
        this(
                address,
                pharmacyInfo.getDutyAddr(),
                pharmacyInfo.getDutyTel1(),
                createOpeningHourDto(pharmacyInfo.getDutyTime1s(), pharmacyInfo.getDutyTime1c()),
                createOpeningHourDto(pharmacyInfo.getDutyTime2s(), pharmacyInfo.getDutyTime2c()),
                createOpeningHourDto(pharmacyInfo.getDutyTime3s(), pharmacyInfo.getDutyTime3c()),
                createOpeningHourDto(pharmacyInfo.getDutyTime4s(), pharmacyInfo.getDutyTime4c()),
                createOpeningHourDto(pharmacyInfo.getDutyTime5s(), pharmacyInfo.getDutyTime5c()),
                createOpeningHourDto(pharmacyInfo.getDutyTime6s(), pharmacyInfo.getDutyTime6c()),
                createOpeningHourDto(pharmacyInfo.getDutyTime7s(), pharmacyInfo.getDutyTime7c())
        );
    }

    private static LocalTime parseTime(String time) {
        if (time == null || time.isBlank()) {
            return null;
        }
        if ("2400".equals(time)) {
            return LocalTime.MIDNIGHT;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(time, formatter);
    }

    private static OpeningHourDto createOpeningHourDto(String startTime, String endTime) {
        LocalTime start = parseTime(startTime);
        LocalTime end = parseTime(endTime);
        return new OpeningHourDto(start, end);
    }
}

