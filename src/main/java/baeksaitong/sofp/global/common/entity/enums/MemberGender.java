package baeksaitong.sofp.global.common.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberGender {
    MALE, FEMALE;

    @JsonCreator
    public static MemberGender from(String s) {
        return MemberGender.valueOf(s.toUpperCase());
    }
}
