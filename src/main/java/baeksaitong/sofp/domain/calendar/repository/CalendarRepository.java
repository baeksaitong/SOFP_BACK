package baeksaitong.sofp.domain.calendar.repository;

import baeksaitong.sofp.domain.calendar.entity.Calendar;
import baeksaitong.sofp.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query("SELECT c.targetProfile FROM Calendar c WHERE c.ownerProfile = :ownerProfile")
    List<Profile> findTargetProfilesByOwnerProfile(@Param("ownerProfile") Profile ownerProfile);
    List<Calendar> findAllByOwnerProfileAndTargetProfileIn(Profile ownerProfile, List<Profile> targetProfileList);
    Boolean existsByOwnerProfile(Profile profile);
}
