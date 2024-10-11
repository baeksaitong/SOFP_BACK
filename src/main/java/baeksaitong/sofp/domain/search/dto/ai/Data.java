package baeksaitong.sofp.domain.search.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Data(@JsonProperty("id") int id, @JsonProperty("result") Result result) {
}
