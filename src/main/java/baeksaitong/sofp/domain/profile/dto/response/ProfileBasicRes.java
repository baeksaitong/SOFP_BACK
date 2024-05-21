package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProfileBasicRes(
        @Schema(name = "프로필 ID")
        Long id,
        @Schema(name = "이름")
        String name,
        @Schema(name = "프로필 사진")
        String imgURL,
        @Schema(name = "색상")
        String color
) {
    public ProfileBasicRes(Profile profile){
        this(profile.getId(), profile.getName(), profile.getImgUrl(), profile.getColor());
    }
}
