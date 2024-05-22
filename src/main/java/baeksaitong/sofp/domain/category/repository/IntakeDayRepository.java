package baeksaitong.sofp.domain.category.repository;

import baeksaitong.sofp.domain.category.entity.IntakeDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntakeDayRepository extends JpaRepository<IntakeDay,Long> {
}
