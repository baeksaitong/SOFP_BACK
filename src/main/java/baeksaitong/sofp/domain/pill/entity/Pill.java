package baeksaitong.sofp.domain.pill.entity;

import baeksaitong.sofp.global.util.EncryptionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial _number")
    private Long serialNumber;
    private String name;
    private String chart;
    private String enterprise;
    private String classification;
    @Column(name = "pro_or_gen")
    private String proOrGen;
    private String formulation;
    @Column(name = "form_classification")
    private String formClassification;
    private String efficacy;
    private String ingredient;
    private String shape;
    @Column(name = "sign_front")
    private String signFront;
    @Column(name = "sign_back")
    private String signBack;
    @Column(name = "line_front")
    private String lineFront;
    @Column(name = "line_back")
    private String lineBack;
    @Column(name = "color_front")
    private String colorFront;
    @Column(name = "color_back")
    private String colorBack;
    @Column(name = "img_url")
    private String imgUrl;

    @JsonIgnore
    public String getEncryptedId(){
        return EncryptionUtil.encrypt(this.id);
    }
}
