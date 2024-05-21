package baeksaitong.sofp.global.s3.error;

import baeksaitong.sofp.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    NO_FILE(HttpStatus.BAD_REQUEST, "S-000", "존재하지 않는 파일 입니다."),
    FAILED_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "파일 업로드에 실패했습니다"),
    FAILED_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "S-002", "파일 삭제에 실패했습니다");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
