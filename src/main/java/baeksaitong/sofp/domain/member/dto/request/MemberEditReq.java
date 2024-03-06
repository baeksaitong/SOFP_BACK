package baeksaitong.sofp.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class MemberEditReq {
    @NotBlank
    private String nickname;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    private String gender;

    @NotBlank
    private String password;

    @NotNull
    private Boolean advertisement;
}
