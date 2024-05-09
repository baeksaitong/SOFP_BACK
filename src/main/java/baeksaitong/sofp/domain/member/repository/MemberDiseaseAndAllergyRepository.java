package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.DiseaseAndAllergy;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberDiseaseAndAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberDiseaseAndAllergyRepository extends JpaRepository<MemberDiseaseAndAllergy, Long> {
    List<MemberDiseaseAndAllergy> findAllByMember(Member member);
    void deleteByMemberAndDiseaseAndAllergy(Member member, DiseaseAndAllergy diseaseAndAllergy);
    boolean existsByMemberAndDiseaseAndAllergy(Member member, DiseaseAndAllergy diseaseAndAllergy);
}
