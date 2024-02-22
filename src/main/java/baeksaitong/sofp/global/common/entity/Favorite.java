package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.SearchType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "pill_id")
    private Pill pill;

    @Enumerated(EnumType.STRING)
    @Column(name = "search_type")
    private SearchType searchType;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "favorite",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomizedPillImg> customizedPillImgs = new ArrayList<>();
}
