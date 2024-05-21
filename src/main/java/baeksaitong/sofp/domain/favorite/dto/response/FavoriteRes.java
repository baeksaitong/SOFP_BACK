package baeksaitong.sofp.domain.favorite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FavoriteRes(
        @Schema(name = "즐겨찾기 알약 리스트")
        List<FavoriteDto> favoriteList
) {
}
