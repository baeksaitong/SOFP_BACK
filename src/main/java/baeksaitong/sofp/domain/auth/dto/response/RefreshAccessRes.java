package baeksaitong.sofp.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshAccessRes(
        @Schema(description = "갱신된 Access Token")
        String accessToken
) {
}
