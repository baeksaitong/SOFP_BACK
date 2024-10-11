package baeksaitong.sofp.domain.search.dto.response;

import baeksaitong.sofp.domain.pill.entity.Pill;
import io.swagger.v3.oas.annotations.media.Schema;

public record KeywordDto(
        @Schema(description = "알약 ID")
        String pillId,
        @Schema(description = "알약 시리얼 번호")
        Long serialNumber,
        @Schema(description = "알약 이름")
        String name,
        @Schema(description = "알약 분류")
        String classification,
        @Schema(description = "일반, 전문 의약품")
        String proOrGen,
        @Schema(description = "알약 이미지")
        String imgUrl,
        @Schema(description = "알약 성상")
        String chart,
        @Schema(description = "알약 제조 업체")
        String enterprise,
        @Schema(description = "경고 여부")
        Boolean isWaring,
        @Schema(description = "즐겨찾기에 존재 시 저장된 즐겨찾기 ID")
        Long FavoriteId,
        @Schema(description = "알약 필터 정보")
        FilterDto filter

) {
        public KeywordDto(Pill pill, Boolean isWaring, Long favoriteId){
                this(pill.getEncryptedId(),
                        pill.getSerialNumber(),
                        pill.getName(),
                        pill.getClassification(),
                        pill.getProOrGen(),
                        pill.getImgUrl(),
                        pill.getChart(),
                        pill.getEnterprise(),
                        isWaring,
                        favoriteId,
                        new FilterDto(pill)
                );
        }
}
