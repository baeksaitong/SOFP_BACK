package baeksaitong.sofp.domain.member.controller;

import baeksaitong.sofp.domain.member.dto.response.*;
import baeksaitong.sofp.domain.member.dto.request.*;
import baeksaitong.sofp.domain.member.service.MemberService;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83D\uDC64 Member")
@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "\uD83D\uDD11 회원 정보 수정 전 비밀번호 인증", description = "회원 수정을 위해 상세정보 페이지로 가기전에 비밀번호로 회원을 인증합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: A-002 | message: 비밀번호가 일치하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/verification")
    public ResponseEntity<String> verification(@ModelAttribute VerificationReq req, @AuthenticationPrincipal Member member){
        memberService.verification(req.getPassword(),member);
        return BaseResponse.ok("인증에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 회원 기본 정보 제공", description = "회원 기본 정보를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 및 프로필 사진 주소 제공")
    })
    @PostMapping("/info/basic")
    public ResponseEntity<BasicInfoRes> getBasicInfo(@AuthenticationPrincipal Member member){
        BasicInfoRes res = memberService.getBasicInfo(member);
        return BaseResponse.ok(res);
    }


    @Operation(summary = "\uD83D\uDD11 회원 상세 정보 제공", description = "회원 상세 정보를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 및 프로필 사진 주소 제공")
    })
    @PostMapping("/info/detail")
    public ResponseEntity<DetailInfoRes> getDetailInfo(@AuthenticationPrincipal Member member){
        DetailInfoRes res = memberService.getDetailInfo(member);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 프로필 사진 등록", description = "프로필 사진을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 사진을 등록에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: S-000 | message: 존재하지 않는 파일 입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "code: S-001 | message: 파일 업로드에 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/img")
    public ResponseEntity<String> setProfileImg(@ModelAttribute ProfileImgReq req, @AuthenticationPrincipal Member member){
        memberService.setProfileImg(req.getProfileImg(),member);
        return BaseResponse.ok("프로필 사진을 등록에 성공했습니다");
    }


    @Operation(summary = "\uD83D\uDD11 닉네임 등록", description = "닉네임을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임을 등록에 성공했습니다")
    })
    @GetMapping("/nickname")
    public ResponseEntity<String> setNickname(@RequestParam String nickname, @AuthenticationPrincipal Member member){
        memberService.setNickName(nickname, member);
        return BaseResponse.ok("닉네임을 등록에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공했습니다")
    })
    @PostMapping("/edit")
    public ResponseEntity<String> editMember(@RequestBody MemberEditReq req,  @AuthenticationPrincipal Member member){
        memberService.editMember(req, member);
        return BaseResponse.ok("회원 정보 수정에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 질병 및 알레르기 정보 조회", description = "질병 및 알레르기 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원이 등록한 질병 및 알레르기 리스트를 반환합니다.")
    })
    @GetMapping("/disease-allergy")
    public ResponseEntity<DiseaseAllergyRes> getDiseaseAllergyList(@AuthenticationPrincipal Member member){
        return BaseResponse.ok(new DiseaseAllergyRes(memberService.getgetDiseaseAllergyList(member)));
    }

    @Operation(summary = "\uD83D\uDD11 질병 및 알레르기 등록", description = "질병 및 알레르기 정보를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 정보 등록에 성공했습니다")
    })
    @PostMapping("/disease-allergy/add")
    public ResponseEntity<String> setDiseaseAllergy(@RequestBody DiseaseAllergyReq req, @AuthenticationPrincipal Member member){
        memberService.setDiseaseAllergy(req.getDiseaseAllergyList(), member);
        return BaseResponse.ok("질병 및 알레르기 정보 등록에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 질병 및 알레르기 정보 수정", description = "질병 및 알레르기 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 및 알레르기 정보 수정에 성공했습니다")
    })
    @PostMapping("/disease-allergy/edit")
    public ResponseEntity<String> editDiseaseAllergy(@RequestBody DiseaseAllergyEditReq req, @AuthenticationPrincipal Member member){
        memberService.removeDiseaseAllergy( req.getRemoveDiseaseAllergyList(), member);
        memberService.setDiseaseAllergy(req.getAddDiseaseAllergyList(), member);
        return BaseResponse.ok("질병 및 알레르기 정보 수정에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 복용 중인 알약 조회", description = "복용 중인 알약을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복용 중인 알약 리스트")
    })
    @PostMapping("/pill")
    public ResponseEntity<PillRes> getPillList(@AuthenticationPrincipal Member member){
        return BaseResponse.ok(memberService.getPillList(member));
    }

    @Operation(summary = "\uD83D\uDD11 복용 중인 알약 등록", description = "복용 중인 알약을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복용 중인 약을 등록에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: P-000 | message: 존재하지 않는 알약입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/pill/add")
    public ResponseEntity<String> setPill(@RequestBody PillReq req, @AuthenticationPrincipal Member member){
        memberService.setPill(req.getPillSerailNumberList(), member);
        return BaseResponse.ok("복용 중인 알약 등록에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 복용 중인 알약 수정", description = "복용 중인 알약을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복용 중인 알약 수정에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: P-000 | message: 존재하지 않는 알약입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/pill/edit")
    public ResponseEntity<String> editPill(@RequestBody PillEditReq req, @AuthenticationPrincipal Member member){
        memberService.removePill(req.getRemovePillSerialNumberList(), member);
        memberService.setPill(req.getAddPillSerialNumberList(), member);
        return BaseResponse.ok("복용 중인 알약 수정에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 최근 본 알약 조회", description = "조회 갯수 최대 40개")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "페이지 갯수, keywordDto 리스트")
    })
    @GetMapping("/recent-view")
    public ResponseEntity<KeywordRes> getRecentViewPill(@RequestParam int count,@AuthenticationPrincipal Member member){
        KeywordRes recentViewPill = memberService.getRecentViewPill(count, member);
        return BaseResponse.ok(recentViewPill);
    }

    @Operation(summary = "\uD83D\uDD11 최근 본 알약 삭제", description = "삭제 후 새로운 리스트 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "페이지 갯수, keywordDto 리스트"),
            @ApiResponse(responseCode = "404", description = "code: H-000 | message: 최근 본 알약 리스트가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/recent-view/delete")
    public ResponseEntity<KeywordRes> deleteRecentViewPill(@RequestParam Long pillId, @RequestParam int count, @AuthenticationPrincipal Member member){
        KeywordRes recentViewPill = memberService.deleteRecentViewPill(pillId, count, member);
        return BaseResponse.ok(recentViewPill);
    }
}
