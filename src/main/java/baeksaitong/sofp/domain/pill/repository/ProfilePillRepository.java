package baeksaitong.sofp.domain.pill.repository;

import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.ProfilePill;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePillRepository extends JpaRepository<ProfilePill, Long> {

    void deleteAllByProfileAndPillIn(Profile profile, List<Pill> pill);
    List<ProfilePill> findAllByProfile(Profile profile);
}
