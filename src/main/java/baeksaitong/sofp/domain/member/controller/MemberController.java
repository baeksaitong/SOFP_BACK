package baeksaitong.sofp.domain.member.controller;

import baeksaitong.sofp.domain.member.dto.request.*;
import baeksaitong.sofp.domain.member.dto.response.AllergyRes;
import baeksaitong.sofp.domain.member.dto.response.DiseaseRes;
import baeksaitong.sofp.domain.member.dto.response.PillRes;
import baeksaitong.sofp.domain.member.service.MemberService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(tags = "3. Member", summary = "프로필 사진 등록", description = "프로필 사진을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 사진을 등록에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: S-000 | message: 존재하지 않는 파일 입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "code: S-001 | message: 파일 업로드에 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/img")
    ResponseEntity<String> setProfileImg(@ModelAttribute ProfileImgReq req, @AuthenticationPrincipal Member member){
        memberService.setProfileImg(req.getProfileImg(),member);
        return BaseResponse.ok("프로필 사진을 등록에 성공했습니다");
    }


    @Operation(tags = "3. Member", summary = "닉네임 등록", description = "닉네임을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임을 등록에 성공했습니다")
    })
    @GetMapping("/nickname")
    ResponseEntity<String> setNickname(@RequestParam String nickname, @AuthenticationPrincipal Member member){
        memberService.setNickName(nickname, member);
        return BaseResponse.ok("닉네임을 등록에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공했습니다")
    })
    @PostMapping("/edit")
    ResponseEntity<String> editMember(@RequestBody MemberEditReq req,  @AuthenticationPrincipal Member member){
        memberService.editMember(req, member);
        return BaseResponse.ok("회원 정보 수정에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "알레르기 조회", description = "알레르기 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원이 등록한 알레르기의 이름 리스트를 반환합니다.")
    })
    @GetMapping("/allergy")
    ResponseEntity<AllergyRes> getAllergy(@AuthenticationPrincipal Member member){
        return BaseResponse.ok(new AllergyRes(memberService.getAllergy(member)));
    }

    @Operation(tags = "3. Member", summary = "알레르기 등록", description = "알레르기 정보를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알레르기 정보 등록에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: A-000 | message: 존재하지 않는 알레르기 정보입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/allergy/add")
    ResponseEntity<String> setAllergy(@RequestBody AllergyReq req, @AuthenticationPrincipal Member member){
        memberService.setAllergy(req.getAllergyList(), member);
        return BaseResponse.ok("알레르기 정보 등록에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "알레르기 수정", description = "알레르기 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알레르기 정보 수정에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: A-000 | message: 존재하지 않는 알레르기 정보입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/allergy/edit")
    ResponseEntity<String> editAllergy(@RequestBody AllergyEditReq req, @AuthenticationPrincipal Member member){
        memberService.removeAllergy( req.getRemoveAllergyList(), member);
        memberService.setAllergy(req.getAddAllergyList(), member);
        return BaseResponse.ok("알레르기 정보 수정에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "질병 정보 조회", description = "질병 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원이 등록한 질병 정보 이름 리스트를 반환합니다.")
    })
    @GetMapping("/disease")
    ResponseEntity<DiseaseRes> getDisease(@AuthenticationPrincipal Member member){
        return BaseResponse.ok(new DiseaseRes(memberService.getDisease(member)));
    }

    @Operation(tags = "3. Member", summary = "질병 등록", description = "질병 정보를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 정보 등록에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: D-000 | message: 존재하지 않는 질병 정보입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/disease/add")
    ResponseEntity<String> setDisease(@RequestBody DiseaseReq req, @AuthenticationPrincipal Member member){
        memberService.setDisease(req.getDiseaseList(), member);
        return BaseResponse.ok("질병 정보 등록에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "질병 정보 수정", description = "질병 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "질병 정보 수정에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: D-000 | message: 존재하지 않는 질병 정보입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/disease/edit")
    ResponseEntity<String> editDisease(@RequestBody DiseaseEditReq req, @AuthenticationPrincipal Member member){
        memberService.removeDisease( req.getRemoveDiseaseList(), member);
        memberService.setDisease(req.getAddDiseaseList(), member);
        return BaseResponse.ok("질병 정보 수정에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "복용 중인 알약 조회", description = "복용 중인 알약을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복용 중인 알약 리스트")
    })
    @PostMapping("/pill")
    ResponseEntity<PillRes> getPill(@AuthenticationPrincipal Member member){
        return BaseResponse.ok(memberService.getPill(member));
    }

    @Operation(tags = "3. Member", summary = "복용 중인 알약 등록", description = "복용 중인 알약을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복용 중인 약을 등록에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: P-000 | message: 존재하지 않는 알약입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/pill/add")
    ResponseEntity<String> setPill(@RequestBody PillReq req, @AuthenticationPrincipal Member member){
        memberService.setPill(req.getPillIdList(), member);
        return BaseResponse.ok("복용 중인 알약 등록에 성공했습니다");
    }

    @Operation(tags = "3. Member", summary = "복용 중인 알약 수정", description = "복용 중인 알약을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "복용 중인 알약 수정에 성공했습니다"),
            @ApiResponse(responseCode = "404", description = "code: P-000 | message: 존재하지 않는 알약입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/pill/edit")
    ResponseEntity<String> editPill(@RequestBody PillEditReq req, @AuthenticationPrincipal Member member){
        memberService.removePill(req.getRemovePillIdList(), member);
        memberService.setPill(req.getAddPillIdList(), member);
        return BaseResponse.ok("복용 중인 알약 수정에 성공했습니다");
    }
}
