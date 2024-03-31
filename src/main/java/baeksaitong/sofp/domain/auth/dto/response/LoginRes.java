package baeksaitong.sofp.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRes(
        @Schema(description = "신규 사용자 여부 - 해당 경우 사용자 설정 과정 수행")
        Boolean isNew,
        @Schema(description = "로그인 토큰")
        String token) {
}
