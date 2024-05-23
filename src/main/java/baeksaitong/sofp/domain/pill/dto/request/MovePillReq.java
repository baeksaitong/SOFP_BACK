package baeksaitong.sofp.domain.pill.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovePillReq {
    private Long pillSerialNumber;
    private Long categoryId;
}
