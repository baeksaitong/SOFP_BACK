package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.DiseaseAllergy;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberDiseaseAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberDiseaseAllergyRepository extends JpaRepository<MemberDiseaseAllergy, Long> {
    List<MemberDiseaseAllergy> findAllByMember(Member member);
    void deleteByMemberAndDiseaseAllergy(Member member, DiseaseAllergy diseaseAllergy);
    boolean existsByMemberAndDiseaseAllergy(Member member, DiseaseAllergy diseaseAllergy);
}
