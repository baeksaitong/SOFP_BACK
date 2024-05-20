package baeksaitong.sofp.domain.history.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryReq {

    private String name;
    private int count;
    private int size=5;
}
