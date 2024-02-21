package baeksaitong.sofp.global.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}
