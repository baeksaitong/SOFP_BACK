package baeksaitong.sofp.domain.favorite.dto.request;

import baeksaitong.sofp.domain.favorite.dto.enums.SearchType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteReq {
    @Schema(description = "검색 타입", example = "COMMON or IMAGE")
    @NotNull(message = "검색 타입이 필요합니다.")
    private SearchType searchType;

    @Schema(description = "알약 고유 번호", example = "200808876")
    @NotNull(message = "알약 고유 번호가 필요합니다.")
    private Long pillSeralNumber;

    @Schema(description = "이미지 검색 시 사용한 이미지")
    private MultipartFile image;
}
