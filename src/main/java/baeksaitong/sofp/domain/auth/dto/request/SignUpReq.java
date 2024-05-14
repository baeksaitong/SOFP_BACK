package baeksaitong.sofp.domain.auth.dto.request;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import baeksaitong.sofp.global.common.validation.ValidEnum;
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
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Schema(description = "이름")
    private String name;
    @NotNull(message = "생년월일이 필요합니다.")
    @Schema(description = "생년월일")
    private LocalDate birthday;
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식이 일치하지 않습니다.")
    @Schema(description = "아이디(이메일)", example = "example@example.com")
    private String email;
    @ValidEnum(enumClass = MemberGender.class, message = "유효하지 않은 성별입니다", ignoreCase=true)
    @Schema(description = "성별", example = "MALE(male)/FEMALE(female)")
    private String gender;
    @Pattern(regexp = PW_REGEXP, message = "유효하지 않는 비밀번호입니다.")
    @Schema(description = "비밀번호 : 8자 이상 16자 이하, 하나 이상 숫자 및 알파벳 문자(소문자 또는 대문자)")
    private String password;
    @NotNull(message = "광고 동의 여부가 필요합니다.")
    @Schema(description = "광고 동의 여부")
    private Boolean advertisement;
}
