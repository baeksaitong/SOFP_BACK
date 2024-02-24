package baeksaitong.sofp.domain.auth.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    DUPLICATIE_ID(HttpStatus.BAD_REQUEST,"A-000","이미 존재하는 아이디입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
