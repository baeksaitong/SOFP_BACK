package baeksaitong.sofp.domain.search.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Result(@JsonProperty("Colors") List<List<Object>> colors, @JsonProperty("Shape") String shape) {
}
