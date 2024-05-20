package baeksaitong.sofp.domain.pill.repository;

import baeksaitong.sofp.domain.search.repository.PillRepositoryCustom;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PillRepository extends JpaRepository<Pill,Long>, PillRepositoryCustom {
    List<Pill> findAllBySerialNumberIn(List<Long> serialNumbers);
}
