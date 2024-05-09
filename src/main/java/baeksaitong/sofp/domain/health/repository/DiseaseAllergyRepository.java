package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.global.common.entity.DiseaseAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseAllergyRepository extends JpaRepository<DiseaseAllergy, Long>, DiseaseAllergyRepositoryCustom {
    List<DiseaseAllergy> findAll();
    Optional<DiseaseAllergy> findByName(String name);
}
