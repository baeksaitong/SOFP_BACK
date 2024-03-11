package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.Allergy;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAllergyRepository extends JpaRepository<MemberAllergy, Long> {
    List<MemberAllergy> findAllByMember(Member member);
    void deleteByMemberAndAllergy(Member member, Allergy allergy);
    boolean existsByMemberAndAllergy(Member member, Allergy allergy);
}
