package baeksaitong.sofp.domain.category.repository;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.IntakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface IntakeTimeRepository extends JpaRepository<IntakeTime,Long> {
    List<IntakeTime> findAllByCategory(Category category);
    List<IntakeTime> findAllByCategoryIn(List<Category> category);
    void deleteAllByCategoryAndTimeIn(Category category, List<LocalTime> timeList);
    void deleteAllByCategory(Category category);
    void deleteAllByCategoryIn(List<Category> category);
}
