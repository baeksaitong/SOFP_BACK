package baeksaitong.sofp.domain.profile.controller;

import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/basic")
    public ResponseEntity<ProfileBasicRes> getProfileBasic(@RequestParam String name, @AuthenticationPrincipal Member member){
        ProfileBasicRes res = profileService.getProfileBasic(name, member);
        return BaseResponse.ok(res);
    }

    @GetMapping("/detail")
    public ResponseEntity<ProfileDetailRes> getProfileDetail(@RequestParam String name, @AuthenticationPrincipal Member member){
        ProfileDetailRes res = profileService.getProfileDetail(name, member);
        return BaseResponse.ok(res);
    }

    @GetMapping("/add")
    public ResponseEntity<String> addProfile(ProfileReq req, @AuthenticationPrincipal Member member){
        profileService.addProfile(req, member);
        return BaseResponse.ok("프로필 추가에 성공했습니다.");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteProfile(@RequestParam String name, @AuthenticationPrincipal Member member){
        profileService.deleteProfile(name, member);
        return BaseResponse.ok("프로필 삭제에 성공했습니다.");
    }
}
