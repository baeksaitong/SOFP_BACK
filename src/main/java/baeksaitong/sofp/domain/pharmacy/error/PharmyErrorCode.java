package baeksaitong.sofp.domain.pharmacy.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PharmyErrorCode implements ErrorCode {
    PHARMY_INFO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"PH-000","약국 정보를 불려오는데 실패했습니다."),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
