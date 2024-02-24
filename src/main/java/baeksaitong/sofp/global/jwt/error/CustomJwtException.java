package baeksaitong.sofp.global.jwt.error;

import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.jwt.error.code.JwtErrorCode;
import lombok.Getter;

@Getter
public class CustomJwtException extends BusinessException {
    public CustomJwtException(JwtErrorCode errorCode) {
        super(errorCode);
    }
}