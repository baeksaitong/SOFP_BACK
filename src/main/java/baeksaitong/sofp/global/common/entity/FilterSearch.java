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
public class FilterSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;
}
