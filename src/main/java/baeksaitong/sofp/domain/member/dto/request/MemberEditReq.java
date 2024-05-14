package baeksaitong.sofp.domain.member.dto.request;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditReq {
    @NotBlank(message = "이름이 필요합니다.")
    @Schema(description = "이름")
    private String name;

    @NotNull(message = "생일이 필요합니다.")
    @Schema(description = "생일")
    private LocalDate birthday;

    @ValidEnum(enumClass = MemberGender.class, message = "유효하지 않은 성별입니다", ignoreCase=true)
    @Schema(description = "성별")
    private String gender;

    @NotBlank(message = "비밀번호가 필요합니다.")
    @Schema(description = "비밀번호")
    private String password;

    @NotNull(message = "광고 동의 여부가 필요합니다.")
    @Schema(description = "광고 동의 여부")
    private Boolean advertisement;
}
