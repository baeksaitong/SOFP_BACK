package baeksaitong.sofp.domain.profile.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileImgEditReq {
    private MultipartFile profileImg;
}
