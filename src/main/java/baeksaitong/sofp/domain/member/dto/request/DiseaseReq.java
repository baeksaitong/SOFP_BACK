package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "초기 사용자 질병 정보 설정")
public class DiseaseReq {
    @Schema(description = "추가할 질병 리스트")
    List<String> diseaseList = new ArrayList<>();
}
