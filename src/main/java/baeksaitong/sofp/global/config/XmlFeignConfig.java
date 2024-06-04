package baeksaitong.sofp.global.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Client;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlFeignConfig {
    @Bean(name = "xmlFeignClient")
    public Client xmlFeignClient() {
        return new Client.Default(null, null);
    }

    @Bean
    public XmlMapper xmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper;
    }

    @Bean(name = "xmlFeignDecoder")
    public Encoder feignEncoder(XmlMapper xmlMapper) {
        return new JacksonEncoder(xmlMapper);
    }

    @Bean(name = "xmlFeignEncoder")
    public Decoder feignDecoder(XmlMapper xmlMapper) {
        return new JacksonDecoder(xmlMapper);
    }
}
