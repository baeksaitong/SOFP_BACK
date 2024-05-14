package baeksaitong.sofp.global.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "\uD83E\uDE7A Health Check")
public class HealthCheckController {

    @Operation(summary = "배포 서버 정상 작동 확인", description = "배포 후 서버 정상 작동을 확인합니다.")
    @GetMapping("/health-check")
    public String healthCheck() {
        return "connect ok";
    }
}
