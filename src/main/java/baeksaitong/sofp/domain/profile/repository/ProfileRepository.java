package baeksaitong.sofp.domain.profile.repository;

import baeksaitong.sofp.domain.member.entity.Member;
import baeksaitong.sofp.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Long countByMember(Member member);
    List<Profile> findAllByMember(Member member);
}
