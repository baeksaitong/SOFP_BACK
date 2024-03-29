package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUid(String uid);
    Boolean existsByUid(String uid);
}
