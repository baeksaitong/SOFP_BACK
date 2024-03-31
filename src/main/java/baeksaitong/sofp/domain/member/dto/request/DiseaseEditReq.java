package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "마이 페이지에서의 질병 정보 수정")
public class DiseaseEditReq {
    @Schema(description = "추가할 질병 정보")
    List<String> addDiseaseList = new ArrayList<>();
    @Schema(description = "삭제 할 질병 정보")
    List<String> removeDiseaseList = new ArrayList<>();
}
