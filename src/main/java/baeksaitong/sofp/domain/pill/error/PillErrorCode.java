package baeksaitong.sofp.domain.pill.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PillErrorCode implements ErrorCode {
    NO_SUCH_PILL(HttpStatus.BAD_REQUEST, "P-000", "존재하지 않는 알약입니다."),
    NO_SUCH_PROFILE_PILL(HttpStatus.BAD_REQUEST, "P-001", "복용중인 알약으로 등록되지 않은 알약입니다."),
    NEED_PROFILE_OR_CATEGORY(HttpStatus.BAD_REQUEST, "P-002", "프로필 정보 혹은 카테고리 정보가 필요합니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}