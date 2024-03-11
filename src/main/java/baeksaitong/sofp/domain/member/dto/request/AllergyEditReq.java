package baeksaitong.sofp.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AllergyEditReq {
    List<String> addAllergyList = new ArrayList<>();
    List<String> removeAllergyList = new ArrayList<>();
}
