package baeksaitong.sofp.domain.auth.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    DUPLICATIE_ID(HttpStatus.BAD_REQUEST,"A-000","이미 존재하는 아이디입니다."),
    NO_SUCH_ID(HttpStatus.BAD_REQUEST,"A-001","존재하지 않는 아이디입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST,"A-002","비밀번호가 일치하지 않습니다..");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
