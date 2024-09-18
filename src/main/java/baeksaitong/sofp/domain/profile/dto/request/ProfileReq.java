package baeksaitong.sofp.domain.profile.dto.request;

import baeksaitong.sofp.domain.profile.entity.enums.Gender;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileReq {
    @NotNull(message = "이름이 필요합니다.")
    @Schema(description = "이름")
    private String name;
    @NotNull(message = "생년월일이 필요합니다.")
    @Schema(description = "생년월일")
    private LocalDate birthday;
    @ValidEnum(enumClass = Gender.class, message = "유효하지 않은 성별입니다", ignoreCase=true)
    @Schema(description = "성별", example = "MALE(male) or FEMALE(female)")
    private String gender;
    @NotNull(message = "색상 정보가 필요합니다.")
    @Schema(description = "색상")
    private String color;
    @Schema(description = "프로필 이미지")
    private MultipartFile profileImg;

    public Gender getGender(){
        return Gender.from(gender);
    }
}
