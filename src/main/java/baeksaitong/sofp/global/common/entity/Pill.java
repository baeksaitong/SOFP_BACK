package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.ColorType;
import baeksaitong.sofp.global.common.entity.enums.FormulationType;
import baeksaitong.sofp.global.common.entity.enums.LineType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "shape_type")
    private ShapeType shapeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "line_type")
    private LineType lineType;

    @Enumerated(EnumType.STRING)
    @Column(name = "color_type")
    private ColorType colorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "formulation_type")
    private FormulationType formulationType;

    @Column(name = "img_url")
    private String imgUrl;
}
