package baeksaitong.sofp.domain.favorite.dto.request;

import baeksaitong.sofp.domain.favorite.dto.enums.SearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class FavoriteReq {
    private SearchType searchType;
    private Long pillId;
    private MultipartFile image;
}
