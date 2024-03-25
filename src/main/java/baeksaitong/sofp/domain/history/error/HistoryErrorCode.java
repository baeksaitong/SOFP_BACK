package baeksaitong.sofp.domain.history.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HistoryErrorCode implements ErrorCode {
    NO_SUCH_RECENT_VIEW_PILL(HttpStatus.BAD_REQUEST,"H-000","최근 본 알약 리스트가 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
