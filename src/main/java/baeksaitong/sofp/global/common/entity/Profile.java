package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile extends BaseTimeEntity{
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

    public void edit(String name, LocalDate birthday, MemberGender gender) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }
}
