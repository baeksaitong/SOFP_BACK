package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "마이 페이지에서의 알레르기 정보 수정")
public class AllergyEditReq {
    @Schema(description = "추가할 알레르기 리스트")
    List<String> addAllergyList = new ArrayList<>();
    @Schema(description = "삭제 할 알레르기 리스트")
    List<String> removeAllergyList = new ArrayList<>();
}
