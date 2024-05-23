package baeksaitong.sofp.domain.category.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Day {
    MON,TUE,WED,THU,FRI,SAT,SUN;
    
    @JsonCreator
    public static Day from(String s) {
        return valueOf(s.toUpperCase());
    }
}
