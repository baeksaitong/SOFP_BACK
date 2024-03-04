package baeksaitong.sofp.domain.health.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DiseaseErrorCode implements ErrorCode {
    NO_SUCH_DISEASE(HttpStatus.BAD_REQUEST, "D-000", "존재하지 않는 질병 정보입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}