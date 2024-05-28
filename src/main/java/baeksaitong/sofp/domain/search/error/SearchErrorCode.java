package baeksaitong.sofp.domain.search.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SearchErrorCode implements ErrorCode {
    PILL_INFO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"S-000","알약 정보를 불려오는데 실패했습니다."),
    NO_SUCH_PILL(HttpStatus.INTERNAL_SERVER_ERROR,"S-001","알약 정보가 존재하지 않습니다"),
    FAILURE_PARSE_XML(HttpStatus.INTERNAL_SERVER_ERROR, "S-002", "알약 정보 변환에 실패했습니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}

