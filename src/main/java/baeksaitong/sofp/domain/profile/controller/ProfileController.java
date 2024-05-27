package baeksaitong.sofp.domain.profile.controller;

import baeksaitong.sofp.domain.profile.dto.response.ProfileListRes;
import baeksaitong.sofp.domain.profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.profile.dto.response.ProfileBasicRes;
import baeksaitong.sofp.domain.profile.dto.response.ProfileDetailRes;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.domain.member.entity.Member;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83D\uDC65 Profile")
@RestController
@RequestMapping("/app/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "\uD83D\uDD11 모든 프로필 조회", description = "모든 프로필의 기본 정보를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 기본 정보 리스트")
    })
    @GetMapping
    public ResponseEntity<ProfileListRes> getProfileList(@AuthenticationPrincipal Member member){
        ProfileListRes res = profileService.getProfileList(member);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 프로필 기본 정보 조회", description = "프로필 ID, 이름, 프로필 사진, 색상 정보를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 프로필 정보 제공"),
            @ApiResponse(responseCode = "404", description = "code: P-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileBasicRes> getProfileBasic(
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        ProfileBasicRes res = profileService.getProfileBasic(profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 프로필 상세 정보 조회", description = "프로필에 대한 모든 정보를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 상세 정보 제공"),
            @ApiResponse(responseCode = "404", description = "code: P-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{profileId}/detail")
    public ResponseEntity<ProfileDetailRes> getProfileDetail(
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        ProfileDetailRes res = profileService.getProfileDetail(profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 프로필 추가", description = "새로운 프로필을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 추가 성공"),
            @ApiResponse(responseCode = "404", description = "code: U-000 | message: 더이상 프로필을 추가할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<String> addProfile(
            @RequestBody @Validated ProfileReq req,
            @AuthenticationPrincipal Member member
    ){
        profileService.addProfile(req, member);
        return BaseResponse.ok("프로필 추가에 성공했습니다.");
    }

    @Operation(summary = "\uD83D\uDD11 프로필 삭제", description = "프로필 ID를 이용하여 프로필을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deleteProfile(
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        profileService.deleteProfile(profileId);
        return BaseResponse.ok("프로필 삭제에 성공했습니다.");
    }

    @Operation(summary = "\uD83D\uDD11 프로필 수정", description = "프로필 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정된 프로필 정보"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{profileId}",  consumes = "multipart/form-data")
    public ResponseEntity<ProfileDetailRes> editProfile(
            @ModelAttribute @Validated ProfileReq req,
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        ProfileDetailRes res = profileService.editProfile(req, profileId);
        return BaseResponse.ok(res);
    }
}
