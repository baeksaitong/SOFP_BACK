package baeksaitong.sofp.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonFeignConfig {
    @Bean(name = "jsonFeignClient")
    public Client jsonFeignClient() {
        return new Client.Default(null, null);
    }

    @Bean(name = "jsonFeignDecoder")
    public Decoder feignDecoder() {
        return new JacksonDecoder(new ObjectMapper());
    }

    @Bean(name = "jsonFeignEncoder")
    public Encoder feignEncoder() {
        return new JacksonEncoder(new ObjectMapper());
    }
}
