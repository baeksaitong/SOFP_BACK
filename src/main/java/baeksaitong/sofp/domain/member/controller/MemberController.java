package baeksaitong.sofp.domain.member.controller;

import baeksaitong.sofp.domain.member.service.MemberService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/mail/sendCode")
    public ResponseEntity<String> sendEmailCode(@RequestParam String email){
        memberService.sendEmailCode(email);
        return BaseResponse.ok("인증 코드를 발송하였습니다.");
    }
}
