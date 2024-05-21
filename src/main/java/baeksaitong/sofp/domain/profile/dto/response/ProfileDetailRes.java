package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.enums.MemberGender;

import java.time.LocalDate;

public record ProfileDetailRes(
        String name,
        LocalDate birthday,
        MemberGender gender,
        String color,
        String imgURL) {
    public ProfileDetailRes(Profile profile){
        this(profile.getName(),profile.getBirthday(), profile.getGender(), profile.getColor(), profile.getImgUrl());
    }
}
