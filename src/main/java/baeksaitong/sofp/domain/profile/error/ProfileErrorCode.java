package baeksaitong.sofp.domain.profile.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfileErrorCode implements ErrorCode {
    EXCEEDED_PROFILE_LIMIT(HttpStatus.BAD_REQUEST,"U-000", "더 이상 프로필을 추가할 수 없습니다."),
    NO_SUCH_PROFILE(HttpStatus.BAD_REQUEST,"U-001", "프로필이 존재하지 않습니다."),
    DUPLICATE_PROFILE(HttpStatus.BAD_REQUEST,"U-002", "이미 존재하는 프로필입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
