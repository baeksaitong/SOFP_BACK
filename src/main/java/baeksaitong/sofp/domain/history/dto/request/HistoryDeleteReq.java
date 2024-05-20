package baeksaitong.sofp.domain.history.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDeleteReq {
    private String name;
    private List<Long> pillIdList;
    private int count;
    private int size=5;
}
