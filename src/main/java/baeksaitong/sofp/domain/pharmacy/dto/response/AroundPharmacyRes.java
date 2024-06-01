package baeksaitong.sofp.domain.pharmacy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AroundPharmacyRes {
    private List<AroundPharmacyDto> aroundPharmacyList;
}
