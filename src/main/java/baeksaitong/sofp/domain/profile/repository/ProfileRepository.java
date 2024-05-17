package baeksaitong.sofp.domain.profile.repository;

import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Long countByMember(Member member);
    Optional<Profile> findByNameAndMember(String name, Member member);
}
