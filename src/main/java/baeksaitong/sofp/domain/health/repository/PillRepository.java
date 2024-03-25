package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.domain.search.repository.PillRepositoryCustom;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PillRepository extends JpaRepository<Pill,Long>, PillRepositoryCustom {
    Optional<Pill> findBySerialNumber(Long serialNumber);
}
