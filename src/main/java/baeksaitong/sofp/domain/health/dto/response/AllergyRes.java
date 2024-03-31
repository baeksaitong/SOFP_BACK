package baeksaitong.sofp.domain.health.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AllergyRes(
        @Schema(description = "알레르기 전체 리스트")
        List<String> allergyList
){

}
