package baeksaitong.sofp.domain.profile.entity;

import baeksaitong.sofp.domain.member.entity.Member;
import baeksaitong.sofp.global.common.entity.BaseTimeEntity;
import baeksaitong.sofp.domain.profile.entity.enums.Gender;
import baeksaitong.sofp.global.util.EncryptionUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Profile extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthday;
    private Gender gender;
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

    public void edit(String name, LocalDate birthday, Gender gender, String color) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.color = color;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEncryptedId(){
        return EncryptionUtil.encrypt(this.id);
    }
}
