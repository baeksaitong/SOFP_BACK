package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.domain.health.entity.DiseaseAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseAllergyRepository extends JpaRepository<DiseaseAllergy, Long>, DiseaseAllergyRepositoryCustom {
    List<DiseaseAllergy> findAll();
    List<DiseaseAllergy> findByNameIn(List<String> names);
}
