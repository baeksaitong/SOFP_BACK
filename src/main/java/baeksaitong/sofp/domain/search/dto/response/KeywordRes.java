package baeksaitong.sofp.domain.search.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record KeywordRes(
        @Schema(description = "검색 결과 총 페이지")
        int totalPage,
        @Schema(description = "검색 결과 알약 리스트")
        List<KeywordDto> result) {
}
