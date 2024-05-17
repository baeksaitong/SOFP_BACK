package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.global.common.entity.Profile;

public record ProfileBasicRes(String name, String imgURL) {
    public ProfileBasicRes(Profile profile){
        this(profile.getName(), profile.getImgUrl());
    }
}
