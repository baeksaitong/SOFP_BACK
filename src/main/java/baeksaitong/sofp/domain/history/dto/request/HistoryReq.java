package baeksaitong.sofp.domain.history.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryReq {
    @Schema(description = "조회할 요소 개수 " +
            "- 기본값 : 40, 입력 가능 범위 : 1~40")
    @Size(min = 1, max = 40, message = "조회 요소 범위는 1 부터 40 이내의 정수입니다.")
    private int count = 40;

    @Schema(description = "한 페이지 당 요소 개수 " +
            "- 기본값 : 5")
    @Size(min = 1, message = "한 페이지 당 요소는 최소 1개 이상의 정수 입니다.")
    private int size=5;
}
