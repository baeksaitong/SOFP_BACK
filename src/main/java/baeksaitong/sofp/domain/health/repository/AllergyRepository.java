package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.global.common.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    List<Allergy> findAll();
    Optional<Allergy> findByName(String name);
}
