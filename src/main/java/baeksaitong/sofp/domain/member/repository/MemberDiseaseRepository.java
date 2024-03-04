package baeksaitong.sofp.domain.member.repository;

import baeksaitong.sofp.global.common.entity.MemberDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDiseaseRepository extends JpaRepository<MemberDisease, Long> {
}
