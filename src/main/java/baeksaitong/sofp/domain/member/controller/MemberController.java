package baeksaitong.sofp.domain.member.controller;

import baeksaitong.sofp.domain.member.dto.response.*;
import baeksaitong.sofp.domain.member.dto.request.*;
import baeksaitong.sofp.domain.member.service.MemberService;
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
    public ResponseEntity<String> verification(@ModelAttribute @Validated VerificationReq req, @AuthenticationPrincipal Member member){
        memberService.verification(req.getPassword(),member);
        return BaseResponse.ok("인증에 성공했습니다");
    }

    @Operation(summary = "\uD83D\uDD11 회원 상세 제공", description = "회원 정보를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 상세 정보")
    })
    @GetMapping
    public ResponseEntity<MemberRes> getMemberInfo(@AuthenticationPrincipal Member member){
        MemberRes res = memberService.getMemberInfo(member);
        return BaseResponse.ok(res);
    }


    @Operation(summary = "\uD83D\uDD11 회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정에 성공했습니다")
    })
    @PutMapping
    public ResponseEntity<MemberRes> editMember(@RequestBody @Validated MemberEditReq req,  @AuthenticationPrincipal Member member){
        MemberRes res = memberService.editMember(req, member);
        return BaseResponse.ok(res);
    }

}
