package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.ShapeType;
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
    private String classification;
    private String formulation;
    private ShapeType shape;
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
}
