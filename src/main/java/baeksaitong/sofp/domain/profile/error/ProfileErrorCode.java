package baeksaitong.sofp.domain.profile.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfileErrorCode implements ErrorCode {
    EXCEEDED_PROFILE_LIMIT(HttpStatus.BAD_REQUEST,"P-000", "더이상 프로필을 추가할 수 없습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
