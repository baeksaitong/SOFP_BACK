package baeksaitong.sofp.domain.profile.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;

public record ProfileBasicRes(Long id, String name, String imgURL, String color) {
    public ProfileBasicRes(Profile profile){
        this(profile.getId(), profile.getName(), profile.getImgUrl(), profile.getColor());
    }
}
