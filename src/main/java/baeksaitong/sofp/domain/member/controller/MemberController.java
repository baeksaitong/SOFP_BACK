package baeksaitong.sofp.domain.member.controller;

import baeksaitong.sofp.domain.member.dto.request.ProfileImgReq;
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
}
