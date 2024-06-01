package baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AroundPharmacyInfoDto {
    private int cnt;
    private double distance;
    private String dutyAddr;
    private String dutyDiv;
    private String dutyDivName;
    private String dutyFax;
    private String dutyName;
    private String dutyTel1;
    private String endTime;
    private String hpid;
    private double latitude;
    private double longitude;
    private int rnum;
    private String startTime;
}
