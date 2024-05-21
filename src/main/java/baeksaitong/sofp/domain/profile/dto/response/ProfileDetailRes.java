package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.entity.enums.Gender;

import java.time.LocalDate;

public record ProfileDetailRes(
        String name,
        LocalDate birthday,
        Gender gender,
        String color,
        String imgURL) {
    public ProfileDetailRes(Profile profile){
        this(profile.getName(),profile.getBirthday(), profile.getGender(), profile.getColor(), profile.getImgUrl());
    }
}
