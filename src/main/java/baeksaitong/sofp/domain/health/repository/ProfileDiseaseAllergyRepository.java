package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.domain.health.entity.DiseaseAllergy;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.health.entity.ProfileDiseaseAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileDiseaseAllergyRepository extends JpaRepository<ProfileDiseaseAllergy, Long> {
    @Query("SELECT p.diseaseAllergy FROM ProfileDiseaseAllergy p WHERE p.profile = :profile")
    List<DiseaseAllergy> findAllDiseaseAllergyByProfile(@Param("profile") Profile profile);
    void deleteAllByProfile(Profile profile);
}
