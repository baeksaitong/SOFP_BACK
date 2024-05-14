package baeksaitong.sofp.domain.verification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static baeksaitong.sofp.global.common.Constants.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailCodeReq {

    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    @Schema(description = "코드를 보낸 이메일", example = "example@example.com")
    private String email;
    @NotBlank(message = "인증 코드가 필요합니다.")
    @Schema(description = "인증 코드")
    private String code;
}
