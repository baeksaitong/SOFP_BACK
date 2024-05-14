package baeksaitong.sofp.domain.member.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationReq {

    @NotBlank(message = "비밀번호가 필요합니다.")
    @Schema(description = "비밀번호")
    private String password;
}
