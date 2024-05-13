package baeksaitong.sofp.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "초기 사용자가 현재 복용중인 알약 설정")
public class PillReq {
    @Schema(description = "추가할 알약 시리얼 번호 리스트")
    List<Long> pillSerailNumberList = new ArrayList<>();
}
