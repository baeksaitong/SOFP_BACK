package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.SearchType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "search_type")
    @Enumerated(value = EnumType.STRING)
    private SearchType searchType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "history",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CamSearch> camSearches = new ArrayList<>();
}
