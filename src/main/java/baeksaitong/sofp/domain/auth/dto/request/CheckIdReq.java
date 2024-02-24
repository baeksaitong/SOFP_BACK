package baeksaitong.sofp.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static baeksaitong.sofp.global.common.Constants.EMAIL_REGEXP;

@Getter
@NoArgsConstructor
public class CheckIdReq {
    @NotBlank
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    private String email;
}
