package baeksaitong.sofp.domain.auth.dto.kakao;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {
    private String email;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String phone;

    @JsonProperty("id")
    private Long id;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    KakaoProfile(@JsonProperty("kakao_account") Map<String,Object> kakaoAccount){
        this.email = (String) kakaoAccount.get("email");
        this.name = (String) kakaoAccount.get("name");
        this.gender = (String) kakaoAccount.get("gender");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String birthyear = (String) kakaoAccount.get("birthyear");
        String birthday = (String) kakaoAccount.get("birthday");
        this.birthday = LocalDate.parse(birthyear+birthday,formatter);

        this.phone = ((String) kakaoAccount.get("phone_number")).replace("+82 ","0").replaceAll("[^0-9]", "");
    }
}
