package baeksaitong.sofp.domain.pill.dto.request;

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
@ToString
public class PillReq {
    @Schema(description = "알약 시리얼 번호 리스트")
    List<Long> pillSeriallNumberList = new ArrayList<>();
}
