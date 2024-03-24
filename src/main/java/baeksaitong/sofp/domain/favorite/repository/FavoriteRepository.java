package baeksaitong.sofp.domain.favorite.repository;

import baeksaitong.sofp.global.common.entity.Favorite;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByMember(Member member);
    Optional<Favorite> findByPillAndMember(Pill pill, Member member);
}
