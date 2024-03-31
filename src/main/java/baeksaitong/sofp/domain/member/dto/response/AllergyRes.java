package baeksaitong.sofp.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AllergyRes(
        @Schema(description = "사용자 알레르기 정보")
        List<String> AllergyList){
}
