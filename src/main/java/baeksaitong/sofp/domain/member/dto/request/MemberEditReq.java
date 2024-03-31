package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditReq {
    @NotBlank
    @Schema(description = "닉네임")
    private String nickname;

    @NotNull
    @Schema(description = "생일")
    private LocalDate birthday;

    @NotBlank
    @Schema(description = "성별")
    private String gender;

    @NotBlank
    @Schema(description = "비밀번호")
    private String password;

    @NotNull
    @Schema(description = "광고 동의 여부")
    private Boolean advertisement;
}
