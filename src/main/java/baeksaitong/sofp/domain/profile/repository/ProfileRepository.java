package baeksaitong.sofp.domain.profile.repository;

import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByMember(Member member);
}
