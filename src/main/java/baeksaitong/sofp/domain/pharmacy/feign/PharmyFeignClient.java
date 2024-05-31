package baeksaitong.sofp.domain.pharmacy.feign;

import baeksaitong.sofp.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PharmyClient", configuration = FeignConfig.class)
public interface PharmyFeignClient {
}
