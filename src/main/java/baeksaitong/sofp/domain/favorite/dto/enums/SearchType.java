package baeksaitong.sofp.domain.favorite.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SearchType {
    COMMON, IMAGE;

    @JsonCreator
    public static SearchType from(String s) {
        return SearchType.valueOf(s.toUpperCase());
    }
}
