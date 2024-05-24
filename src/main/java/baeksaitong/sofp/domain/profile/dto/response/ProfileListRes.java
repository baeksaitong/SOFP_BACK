package baeksaitong.sofp.domain.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ProfileListRes(
        @Schema(description = "모든 사용자 프로필 기본 정보 리스트")
        List<ProfileBasicRes> profileList
) {
}
