package baeksaitong.sofp.domain.search.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
public class KeywordReq {
    private String keyword;
    private String shape;
    private String sign;
    private String color;
    private String formulation;
    private String line;
    private int page=0;
    private int limit=5;
}
