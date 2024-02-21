package baeksaitong.sofp.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(title = "📝 약속(악을 찾는 속도) API 명세서",
                description = " 약속(악을 찾는 속도) 서비스의 API 명세서입니다.",
                version = "v1"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
}
