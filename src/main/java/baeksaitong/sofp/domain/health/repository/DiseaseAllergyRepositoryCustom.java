package baeksaitong.sofp.domain.health.repository;

import java.util.List;

public interface DiseaseAllergyRepositoryCustom {
    List<String> findByKeyword(String keyword);
}
