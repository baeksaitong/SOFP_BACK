package baeksaitong.sofp.domain.search.feign;

import baeksaitong.sofp.domain.search.dto.ai.AIAnalyzeDto;
import baeksaitong.sofp.global.config.MultiPartFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "AIClient", url = "http://3.35.119.222:5000", configuration = MultiPartFeignConfig.class)
public interface AIFeignClient {
    @PostMapping(value = "/analyze", consumes = "multipart/form-data")
    AIAnalyzeDto getAIAnalyze(@RequestPart("file") MultipartFile file);

}
