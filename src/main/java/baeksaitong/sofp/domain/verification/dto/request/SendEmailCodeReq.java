package baeksaitong.sofp.domain.verification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static baeksaitong.sofp.global.common.constants.Constants.EMAIL_REGEXP;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendEmailCodeReq {
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    @Schema(description = "코드 전송할 이메일", example = "example@example.com")
    private String email;
}
