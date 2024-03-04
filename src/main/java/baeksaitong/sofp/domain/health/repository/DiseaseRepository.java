package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.global.common.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    List<Disease> findAll();

    Optional<Disease> findByName(String name);
}
