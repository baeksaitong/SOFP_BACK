package baeksaitong.sofp.domain.favorite.repository;

import baeksaitong.sofp.domain.favorite.entity.Favorite;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByProfile(Profile profile);
    Optional<Favorite> findByPillAndProfile(Pill pill, Profile profile);
    Boolean existsByPillAndProfile(Pill pill, Profile profile);
}
