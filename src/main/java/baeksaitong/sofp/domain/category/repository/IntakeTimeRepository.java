package baeksaitong.sofp.domain.category.repository;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.IntakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntakeTimeRepository extends JpaRepository<IntakeTime,Long> {
    List<IntakeTime> findAllByCategory(Category category);

}
