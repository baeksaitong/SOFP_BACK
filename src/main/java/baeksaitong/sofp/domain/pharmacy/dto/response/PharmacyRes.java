package baeksaitong.sofp.domain.pharmacy.dto.response;

import baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo.PharmacyInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record PharmacyRes (
        @Schema(description = "일반 주소")
        String address,
        @Schema(description = "도로명 주소")
        String roadAddress,
        @Schema(description = "전화번호")
        String phone,
        @Schema(description = "월요일 영업시간")
        OpeningHourDto mon,
        @Schema(description = "화요일 영업시간")
        OpeningHourDto tue,
        @Schema(description = "수요일 영업시간")
        OpeningHourDto wed,
        @Schema(description = "목요일 영업시간")
        OpeningHourDto thu,
        @Schema(description = "금요일 영업시간")
        OpeningHourDto fri,
        @Schema(description = "토요일 영업시간")
        OpeningHourDto sat,
        @Schema(description = "일요일 영업시간")
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

