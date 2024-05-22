package baeksaitong.sofp.domain.category.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    NO_SUCH_Category(HttpStatus.BAD_REQUEST, "C-000", "존재하지 않는 카테고리입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
