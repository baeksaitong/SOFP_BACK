package baeksaitong.sofp.domain.favorite.dto.response;

import java.util.List;

public record FavoriteRes(
        List<FavoriteDto> favoriteList
) {
}
