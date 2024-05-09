package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.global.common.entity.DiseaseAndAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseAndAllergyRepository extends JpaRepository<DiseaseAndAllergy, Long> {
    List<DiseaseAndAllergy> findAll();

    Optional<DiseaseAndAllergy> findByName(String name);
}
