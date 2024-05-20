package baeksaitong.sofp.domain.member.dto.response;

import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;

import java.util.List;

public record ProfileListRes(
        List<ProfileBasicRes> profileList
) {
}
