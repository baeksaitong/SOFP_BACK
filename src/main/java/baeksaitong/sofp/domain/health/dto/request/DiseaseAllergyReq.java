package baeksaitong.sofp.domain.health.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseAllergyReq {
    private List<String> diseaseAllergyList = new ArrayList<>();
}
