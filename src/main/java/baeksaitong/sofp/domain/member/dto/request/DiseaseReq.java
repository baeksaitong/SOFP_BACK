package baeksaitong.sofp.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiseaseReq {
    List<String> diseaseList = new ArrayList<>();
}
