package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "마이 페이지에서의 내가 복용중인 알약 정보 수정")
public class PillEditReq {
    @Schema(description = "추가할 알약 시리얼 번호 리스트")
    List<Long> addPillIdList = new ArrayList<>();
    @Schema(description = "삭제할 알약 시리얼 번호 리스트")
    List<Long> removePillIdList = new ArrayList<>();
}
