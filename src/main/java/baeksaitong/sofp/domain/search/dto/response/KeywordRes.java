package baeksaitong.sofp.domain.search.dto.response;

import java.util.List;

public record KeywordRes(int totalPage, List<KeywordDto> result) {
}
