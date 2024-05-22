package baeksaitong.sofp.domain.category.repository;

import baeksaitong.sofp.domain.category.entity.IntakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntakeTimeRepository extends JpaRepository<IntakeTime,Long> {
}
