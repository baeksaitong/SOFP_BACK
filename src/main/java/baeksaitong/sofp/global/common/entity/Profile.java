package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthday;
    private MemberGender gender;
    private String color;
    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Transient
    public void initialize() {
        Hibernate.initialize(this);
    }

    public void edit(String name, LocalDate birthday, MemberGender gender, String color) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.color = color;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
