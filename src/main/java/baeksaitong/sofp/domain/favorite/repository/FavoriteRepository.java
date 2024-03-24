package baeksaitong.sofp.domain.favorite.repository;

import baeksaitong.sofp.global.common.entity.Favorite;
import baeksaitong.sofp.global.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByMember(Member member);
}
