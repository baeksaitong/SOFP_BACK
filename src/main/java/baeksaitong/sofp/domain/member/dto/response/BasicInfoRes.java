package baeksaitong.sofp.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BasicInfoRes(
        @Schema(description = "닉네임")
        String nickName,
        @Schema(description = "프로필 사진 주소")
        String imgUrl) {
}
