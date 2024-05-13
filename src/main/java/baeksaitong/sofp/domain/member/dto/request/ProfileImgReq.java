package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class ProfileImgReq {

    @NotNull(message = "사용자 이미지가 필요합니다.")
    @Schema(description = "사용자 프로필 이미지")
    private MultipartFile profileImg;
}
