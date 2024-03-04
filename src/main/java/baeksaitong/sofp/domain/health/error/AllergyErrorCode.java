package baeksaitong.sofp.domain.health.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AllergyErrorCode implements ErrorCode {
    NO_SUCH_ALLERGY(HttpStatus.BAD_REQUEST, "A-000", "존재하지 않는 알레르기 정보입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}