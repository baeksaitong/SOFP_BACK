package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditReq {
    @NotBlank(message = "비밀번호가 필요합니다.")
    @Schema(description = "비밀번호")
    private String password;

    @NotNull(message = "광고 동의 여부가 필요합니다.")
    @Schema(description = "광고 동의 여부")
    private Boolean advertisement;
}
