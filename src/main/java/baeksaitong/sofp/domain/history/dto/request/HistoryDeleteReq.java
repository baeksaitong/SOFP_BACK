package baeksaitong.sofp.domain.history.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDeleteReq {
    @Schema(description = "삭제 할 최근 본 알약 시리얼 번호 리스트")
    private List<Long> pillIdList = new ArrayList<>();
    @Schema(description = "조회할 요소 개수 " +
            "- 기본값 : 40, 입력 가능 범위 : 1~40")
    @Size(min = 1, max = 40, message = "조회 요소 범위는 1 부터 40 이내의 정수입니다.")
    private int count;
    @Schema(description = "한 페이지 당 요소 개수 " +
            "- 기본값 : 5, 최소값 : 1")
    @Size(min = 1, message = "한 페이지 당 요소는 최소 1개 이상의 정수 입니다.")
    private int size=5;
}
