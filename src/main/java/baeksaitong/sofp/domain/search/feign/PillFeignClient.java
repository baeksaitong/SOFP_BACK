package baeksaitong.sofp.domain.search.feign;

import baeksaitong.sofp.domain.search.dto.pillInfo.PillInfoDto;
import baeksaitong.sofp.global.config.JsonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "PillClient", configuration = JsonFeignConfig.class)
public interface PillFeignClient {
    @GetMapping
    PillInfoDto getPillInfo(URI baseUrl,
                            @RequestParam String serviceKey,
                            @RequestParam String type,
                            @RequestParam("item_seq") String serialNumber);
}
