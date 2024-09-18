package baeksaitong.sofp.domain.health.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "마이 페이지에서의 질병 및 알레르기 정보 수정")
@ToString
public class DiseaseAllergyEditReq {
    @Schema(description = "추가할 질병 및 알레르기 리스트")
    List<String> addDiseaseAllergyList = new ArrayList<>();
    @Schema(description = "삭제 할 질병 및 알레르기 리스트")
    List<String> removeDiseaseAllergyList = new ArrayList<>();
}
