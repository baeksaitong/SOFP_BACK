package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProfileBasicDto(
        @Schema(description = "사용자 ID")
        String profileId,
        @Schema(description = "색상")
        String color
) {
    public ProfileBasicDto(Profile profile){
        this(profile.getEncryptedId(), profile.getColor());
    }
}
