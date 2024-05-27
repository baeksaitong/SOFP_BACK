package baeksaitong.sofp.domain.favorite.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FavoriteReq {
    @Schema(description = "알약 고유 번호", example = "200808876")
    @NotNull(message = "알약 고유 번호가 필요합니다.")
    private Long pillSerialNumber;

    @Schema(description = "이미지 검색 시 사용한 이미지")
    private MultipartFile image;
}
