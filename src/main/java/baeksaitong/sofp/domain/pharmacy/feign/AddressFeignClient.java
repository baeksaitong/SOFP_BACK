package baeksaitong.sofp.domain.pharmacy.feign;

import baeksaitong.sofp.domain.pharmacy.dto.address.Address;
import baeksaitong.sofp.global.config.JsonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "AddressClient", configuration = JsonFeignConfig.class)
public interface AddressFeignClient {
    @GetMapping
    Address getAddress(URI baseUrl,
                       @RequestParam("confmKey") String serviceKey,
                       @RequestParam("currentPage") int currentPage,
                       @RequestParam("countPerPage") int countPerPage,
                       @RequestParam("keyword") String keyword,
                       @RequestParam("resultType") String resultType);
}
