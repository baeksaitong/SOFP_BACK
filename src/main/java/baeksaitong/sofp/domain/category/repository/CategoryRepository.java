package baeksaitong.sofp.domain.category.repository;

import baeksaitong.sofp.domain.category.entity.Category;
import baeksaitong.sofp.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndProfile(String name, Profile profile);

    Optional<Category> findByIdAndProfile(Long id, Profile profile);
    List<Category> findAllByProfile(Profile profile);
}
