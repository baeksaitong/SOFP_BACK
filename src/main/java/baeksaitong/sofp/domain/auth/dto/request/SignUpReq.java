package baeksaitong.sofp.domain.auth.dto.request;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

import static baeksaitong.sofp.global.common.Constants.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpReq {
    @NotBlank
    @Schema(description = "이름")
    private String name;

    @NotNull
    @Schema(description = "생일")
    private LocalDate birthday;

    @NotBlank
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    @Schema(description = "아이디(이메일)", example = "example@example.com")
    private String email;

    @NotNull
    @Schema(description = "성별")
    private MemberGender gender;

    @NotBlank
    @Schema(description = "비밀번호")
    private String password;

    @NotNull
    @Schema(description = "광고 동의 여부")
    private Boolean advertisement;
}
