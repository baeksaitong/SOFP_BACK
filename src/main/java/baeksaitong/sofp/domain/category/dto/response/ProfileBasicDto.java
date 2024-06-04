package baeksaitong.sofp.domain.category.dto.response;

import baeksaitong.sofp.domain.profile.entity.Profile;

public record ProfileBasicDto(
        String profileId,
        String color
) {
    public ProfileBasicDto(Profile profile){
        this(profile.getEncryptedId(), profile.getColor());
    }
}
