package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.ProfileDiseaseAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileDiseaseAllergyRepository extends JpaRepository<ProfileDiseaseAllergy, Long> {
    List<ProfileDiseaseAllergy> findAllByProfile(Profile profile);
}
