package baeksaitong.sofp.domain.search.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ImageRes(
        @Schema(description = "이미지 검색에 사용된 필터 정보")
        FilterDto filter,
        @Schema(description = "검색 결과 알약 리스트")
        List<KeywordDto> result

) {
}
