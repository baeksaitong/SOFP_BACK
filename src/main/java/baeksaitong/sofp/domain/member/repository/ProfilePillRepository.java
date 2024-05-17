package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.ProfilePill;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePillRepository extends JpaRepository<ProfilePill, Long> {
    boolean existsByMemberAndPill(Member member, Pill pill);
    void deleteByMemberAndPill(Member member, Pill pill);
    List<ProfilePill> findAllByMember(Member member);
}
