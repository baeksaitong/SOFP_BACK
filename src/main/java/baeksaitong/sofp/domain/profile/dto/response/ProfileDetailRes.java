package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.entity.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record ProfileDetailRes(
        @Schema(description = "프로필 ID")
        Long id,
        @Schema(description = "이름")
        String name,
        @Schema(description = "생일")
        LocalDate birthday,
        @Schema(description = "성별")
        Gender gender,
        @Schema(description = "색상")
        String color,
        @Schema(description = "프로필 이미지")
        String imgURL
) {
    public ProfileDetailRes(Profile profile){
        this(profile.getId(), profile.getName(),profile.getBirthday(), profile.getGender(), profile.getColor(), profile.getImgUrl());
    }
}
