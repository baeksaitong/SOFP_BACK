package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProfileBasicRes(
        @Schema(description = "프로필 ID")
        String id,
        @Schema(description = "이름")
        String name,
        @Schema(description = "프로필 사진")
        String imgURL,
        @Schema(description = "색상")
        String color
) {
    public ProfileBasicRes(Profile profile){
        this(profile.getEncryptedId(), profile.getName(), profile.getImgUrl(), profile.getColor());
    }
}
