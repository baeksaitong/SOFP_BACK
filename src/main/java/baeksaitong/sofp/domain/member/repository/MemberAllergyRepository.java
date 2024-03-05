package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.Allergy;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAllergyRepository extends JpaRepository<MemberAllergy, Long> {
    void deleteByMemberAndAllergy(Member member, Allergy allergy);

}
