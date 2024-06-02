package baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PharmacyInfo {
    private String dutyAddr;
    private String dutyName;
    private String dutyTel1;
    private String dutyTime1c;
    private String dutyTime1s;
    private String dutyTime2c;
    private String dutyTime2s;
    private String dutyTime3c;
    private String dutyTime3s;
    private String dutyTime4c;
    private String dutyTime4s;
    private String dutyTime5c;
    private String dutyTime5s;
    private String dutyTime6c;
    private String dutyTime6s;
    private String dutyTime7c;
    private String dutyTime7s;
    private String hpid;
    private String postCdn1;
    private String postCdn2;
    private double wgs84Lat;
    private double wgs84Lon;
}
