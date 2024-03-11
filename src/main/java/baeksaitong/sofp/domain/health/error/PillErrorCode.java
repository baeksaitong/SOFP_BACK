package baeksaitong.sofp.domain.health.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PillErrorCode implements ErrorCode {
    NO_SUCH_PILL(HttpStatus.BAD_REQUEST, "P-000", "존재하지 않는 알약입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}