package baeksaitong.sofp.domain.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KeywordDto {
    @Schema(description = "검색 키워드 - 알약 이름, 성분, 효능 정보",example = "가스디알정")
    private String keyword;
    @Schema(description = "알약 모양", example = "원형")
    private String shape;
    @Schema(description = "알약에 적힌 기호", example = "IDG")
    private String sign;
    @Schema(description = "알약 색깔", example = "연두")
    private String color;
    @Schema(description = "알약 제형", example = "경질/연질/정제")
    private String formulation;
    @Schema(description = "알약 분할선", example = "-/+")
    private String line;
    @Schema(description = "마지막 요소(추가 요소 요청 시 이전 결과의 마지막 요소의 알약 ID", defaultValue = "0")
    private Long lastId;
    @Schema(description = "페이지 당 요소 갯수", defaultValue = "10")
    private int limit=10;
}
