package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.entity.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record ProfileDetailRes(
        @Schema(name = "프로필 ID")
        Long id,
        @Schema(name = "이름")
        String name,
        @Schema(name = "생일")
        LocalDate birthday,
        @Schema(name = "성별")
        Gender gender,
        @Schema(name = "색상")
        String color,
        @Schema(name = "프로필 이미지")
        String imgURL
) {
    public ProfileDetailRes(Profile profile){
        this(profile.getId(), profile.getName(),profile.getBirthday(), profile.getGender(), profile.getColor(), profile.getImgUrl());
    }
}
