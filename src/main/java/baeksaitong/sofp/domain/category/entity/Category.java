package baeksaitong.sofp.domain.category.entity;

import baeksaitong.sofp.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean alarm;
    private LocalDate period;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void edit(String name, Boolean alarm, LocalDate period){
        this.name = name;
        this.alarm = alarm;
        this.period = period;
    }

}
