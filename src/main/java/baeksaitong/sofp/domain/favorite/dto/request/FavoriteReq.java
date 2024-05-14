package baeksaitong.sofp.domain.favorite.dto.request;

import baeksaitong.sofp.domain.favorite.dto.enums.SearchType;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FavoriteReq {
    @Schema(description = "검색 타입", example = "COMMON(common)/IMAGE(image)")
    @ValidEnum(enumClass = SearchType.class, message = "검색 타입이 필요합니다.", ignoreCase = true)
    private String searchType;

    @Schema(description = "알약 고유 번호", example = "200808876")
    @NotNull(message = "알약 고유 번호가 필요합니다.")
    private Long pillSeralNumber;

    @Schema(description = "이미지 검색 시 사용한 이미지")
    private MultipartFile image;
}
