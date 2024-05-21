package baeksaitong.sofp.domain.favorite.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {
    DUPLICATE_FAVORITE(HttpStatus.BAD_REQUEST,"F-000","이미 즐겨찾기에 추가되어 있습니다."),
    NO_SUCH_FAVORITE(HttpStatus.BAD_REQUEST,"F-001","해당 즐겨찾기 정보가 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
