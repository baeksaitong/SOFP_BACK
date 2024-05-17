package baeksaitong.sofp.domain.Profile.service;

import baeksaitong.sofp.domain.Profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.Profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
}
