package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.Disease;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberDisease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberDiseaseRepository extends JpaRepository<MemberDisease, Long> {
    List<MemberDisease> findAllByMember(Member member);
    void deleteByMemberAndDisease(Member member, Disease disease);
    boolean existsByMemberAndDisease(Member member, Disease disease);
}
