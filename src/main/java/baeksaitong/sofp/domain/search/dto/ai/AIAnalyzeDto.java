package baeksaitong.sofp.domain.search.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AIAnalyzeDto(@JsonProperty("data") Data data) {
    public List<String> getColor(){
        return data.result().colors().stream().map(
                objects -> (String) objects.get(0)
        ).toList();
    }

    public String getShape(){
        return data.result().shape();
    }
}
