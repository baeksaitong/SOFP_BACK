package baeksaitong.sofp.domain.pill.repository;

import baeksaitong.sofp.domain.search.repository.PillRepositoryCustom;
import baeksaitong.sofp.domain.pill.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PillRepository extends JpaRepository<Pill,Long>, PillRepositoryCustom {
    List<Pill> findAllBySerialNumberIn(List<Long> serialNumbers);
    Optional<Pill> findBySerialNumber(Long serialNumber);
}
