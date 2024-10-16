package baeksaitong.sofp.domain.history.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryDeleteReq {
    @Schema(description = "삭제 할 최근 본 알약 시리얼 번호 리스트")
    private List<String> pillIdList = new ArrayList<>();
    @Schema(description = "조회할 요소 개수 " +
            "- 기본값 : 40, 입력 가능 범위 : 1~40")
    @Size(min = 1, max = 40, message = "조회 요소 범위는 1 부터 40 이내의 정수입니다.")
    private int count;
}
