package baeksaitong.sofp.domain.member.dto.request;

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
@Schema(description = "마이 페이지에서의 내가 복용중인 알약 정보 수정")
@ToString
public class PillEditReq {
    @Schema(description = "추가할 알약 시리얼 번호 리스트")
    List<Long> addPillSerialNumberList = new ArrayList<>();
    @Schema(description = "삭제할 알약 시리얼 번호 리스트")
    List<Long> removePillSerialNumberList = new ArrayList<>();
}
