package baeksaitong.sofp.domain.profile.dto.request;

import baeksaitong.sofp.domain.profile.entity.enums.Gender;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProfileReq {
    private String name;
    private LocalDate birthday;
    private String gender;
    private String color;
    private MultipartFile profileImg;

    public Gender getGender(){
        return Gender.from(gender);
    }
}
