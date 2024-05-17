package baeksaitong.sofp.domain.Profile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProfileReq {
    private String name;
    private LocalDate birthday;
    private String gender;
}
