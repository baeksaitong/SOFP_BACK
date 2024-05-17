package baeksaitong.sofp.domain.profile.dto.request;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileReq {
    private String name;
    private LocalDate birthday;
    private String gender;

    public MemberGender getGender(){
        return MemberGender.from(gender);
    }
}
