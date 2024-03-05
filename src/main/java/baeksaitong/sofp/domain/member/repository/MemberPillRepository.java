package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberPill;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPillRepository extends JpaRepository<MemberPill, Long> {
    boolean existsByMemberAndPill(Member member, Pill pill);

    void deleteByMemberAndPill(Member member, Pill pill);
}
