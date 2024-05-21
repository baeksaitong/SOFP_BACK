package baeksaitong.sofp.domain.profile.controller;

import baeksaitong.sofp.domain.profile.dto.request.ProfileImgEditReq;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/basic")
    public ResponseEntity<ProfileBasicRes> getProfileBasic(@RequestParam Long profileId){
        ProfileBasicRes res = profileService.getProfileBasic(profileId);
        return BaseResponse.ok(res);
    }

    @GetMapping("/detail")
    public ResponseEntity<ProfileDetailRes> getProfileDetail(@RequestParam Long profileId){
        ProfileDetailRes res = profileService.getProfileDetail(profileId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProfile(@RequestBody ProfileReq req, @AuthenticationPrincipal Member member){
        profileService.addProfile(req, member);
        return BaseResponse.ok("프로필 추가에 성공했습니다.");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteProfile(@RequestParam Long profileId){
        profileService.deleteProfile(profileId);
        return BaseResponse.ok("프로필 삭제에 성공했습니다.");
    }

    @PostMapping("/edit")
    public ResponseEntity<ProfileDetailRes> editProfile(@RequestBody ProfileReq req, @RequestParam Long profileId){
        ProfileDetailRes res = profileService.editProfile(req, profileId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/img")
    public ResponseEntity<String> setProfileImg( @ModelAttribute ProfileImgEditReq req, @RequestParam Long profileId){
        profileService.setProfileImg(req, profileId);
        return BaseResponse.ok("프로필 사진을 등록에 성공했습니다");
    }
}
