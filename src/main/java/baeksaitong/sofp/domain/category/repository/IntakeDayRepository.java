package baeksaitong.sofp.domain.category.repository;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.category.entity.IntakeDay;
import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntakeDayRepository extends JpaRepository<IntakeDay,Long> {
    List<IntakeDay> findAllByCategory(Category category);
    @EntityGraph(attributePaths = {"category", "category.profile"})
    List<IntakeDay> findAllByDayAndProfileIn(Day day, List<Profile> profiles);
}
