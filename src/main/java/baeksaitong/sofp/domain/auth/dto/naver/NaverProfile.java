package baeksaitong.sofp.domain.auth.dto.naver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
public class NaverProfile {
    private String id;
    private String email;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String phone;
    private String profileImgUrl;

    @JsonCreator
    public NaverProfile(@JsonProperty("response") Map<String,String> response){
        this.id = response.get("id");
        this.email = response.get("email");
        this.name = response.get("name");
        this.gender = response.get("gender");
        this.profileImgUrl = response.get("profile_image");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String birthyear = response.get("birthyear");
        String birthday = response.get("birthday");
        this.birthday = LocalDate.parse(birthyear+"-"+birthday,formatter);

        this.phone = response.get("mobile");
    }
}
