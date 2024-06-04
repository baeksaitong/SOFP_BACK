package baeksaitong.sofp.domain.pharmacy.dto.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String roadAddress;
    private String address;

    @JsonCreator
    public Address(@JsonProperty("results") Map<String, Object> result) {
        if (result != null) {
            Map<String, Object> juso = (Map<String, Object>) ((List<Object>)result.get("juso")).get(0); // 첫 번째 juso 객체를 사용
            this.roadAddress = (String) juso.get("roadAddr");
            this.address = (String) juso.get("jibunAddr");
        }
    }
}
