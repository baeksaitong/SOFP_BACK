package baeksaitong.sofp.domain.search.dto.ai;

import java.util.List;

public record AIAnalyzeDto(String color, int id, String shape) {
    public List<String> getColorList() {
        return List.of(color.replace(" ", "").split(","));
    }
}
