package baeksaitong.sofp.domain.favorite.repository;

import baeksaitong.sofp.global.common.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
