package baeksaitong.sofp.domain.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BasicInfoRes(
        @Schema(description = "이름")
        String name,
        @Schema(description = "프로필 사진 주소")
        String imgUrl
) {
}
