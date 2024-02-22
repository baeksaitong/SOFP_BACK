package baeksaitong.sofp.global.common.entity;

import baeksaitong.sofp.global.common.entity.enums.SearchType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
