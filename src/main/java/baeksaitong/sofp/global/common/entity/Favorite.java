package baeksaitong.sofp.global.common.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "img_url")
    @Nullable
    private String imgUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pill_id")
    private Pill pill;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
