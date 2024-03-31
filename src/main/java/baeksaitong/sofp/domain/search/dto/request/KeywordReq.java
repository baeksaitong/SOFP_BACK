package baeksaitong.sofp.domain.search.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordReq {
    @Schema(description = "검색 키워드 - 현제는 알약 이름만 가능")
    private String keyword;
    @Schema(description = "알약 모양")
    private String shape;
    @Schema(description = "알약에 적힌 기호")
    private String sign;
    @Schema(description = "알약 색깔")
    private String color;
    @Schema(description = "알약 제형")
    private String formulation;
    @Schema(description = "알약 분할선")
    private String line;
    @Schema(description = "검색 페이지 - 0부터 시작", defaultValue = "0")
    private int page=0;
    @Schema(description = "페이지 당 요소 갯수", defaultValue = "5")
    private int limit=5;
}
