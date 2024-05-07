package baeksaitong.sofp.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenReq(
        @Schema(description = "Refresh Token")
        @NotBlank
        String refreshToken
) {
}
