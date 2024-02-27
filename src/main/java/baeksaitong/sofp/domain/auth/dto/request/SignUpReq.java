package baeksaitong.sofp.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static baeksaitong.sofp.global.common.Constants.*;

@Getter
@NoArgsConstructor
public class SignUpReq {
    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = PHONE_NUM_REGEXP, message = "올바르지 않은 전화번호 입니다.")
    private String phone;

    @NotNull
    private Boolean advertisement;
}
