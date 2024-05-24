package baeksaitong.sofp.domain.calendar.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EditTargetProfile {
    private List<Long> addTargetProfileIdList;
    private List<Long> deleteTargetProfileIdList;
}
