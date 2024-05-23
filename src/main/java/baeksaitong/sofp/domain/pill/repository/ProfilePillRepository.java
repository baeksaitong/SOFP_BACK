package baeksaitong.sofp.domain.pill.repository;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.pill.entity.ProfilePill;
import baeksaitong.sofp.domain.pill.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePillRepository extends JpaRepository<ProfilePill, Long> {

    void deleteAllByProfileAndPillIn(Profile profile, List<Pill> pill);
    List<ProfilePill> findAllByProfile(Profile profile);
    List<ProfilePill> findAllByCategory(Category category);
    List<ProfilePill> findAllByProfileAndCategoryIsNull(Profile profile);
}
