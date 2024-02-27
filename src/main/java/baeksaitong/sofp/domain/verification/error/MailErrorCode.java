package baeksaitong.sofp.domain.verification.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MailErrorCode implements ErrorCode {
    FAILED_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR,"M-000","메일 전송에 실패했습니다."),
    EXPIRED_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "M-001", "코드 유효기간이 만료 되었습니다."),
    INCORRECT_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "M-002", "코드가 일치하지 않습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
