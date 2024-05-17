package baeksaitong.sofp.domain.profile.controller;

import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    public ResponseEntity<String> addProfile(ProfileReq req, @AuthenticationPrincipal Member member){
        profileService.addProfile(req, member);
        return BaseResponse.ok("개발중");
    }
}
