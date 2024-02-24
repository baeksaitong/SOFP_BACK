package baeksaitong.sofp.domain.member.controller;

import baeksaitong.sofp.domain.member.dto.request.CheckEmailCodeReq;
import baeksaitong.sofp.domain.member.service.MemberService;
import baeksaitong.sofp.global.common.Constants;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/mail/sendCode")
    public ResponseEntity<String> sendEmailCode(@RequestParam @Valid @Pattern(regexp = Constants.EMAil_REGEXP, message = "이메일 형식이 일치하지 않습니다.") String email){
        memberService.sendEmailCode(email);
        return BaseResponse.ok("인증 코드를 발송하였습니다.");
    }

    @PostMapping("/mail/checkCode")
    public ResponseEntity<Boolean> checkEmailCode(@RequestBody @Valid CheckEmailCodeReq req){
        boolean verified = memberService.checkEmailCode(req.getEmail(), req.getCode());
        return BaseResponse.ok(verified);
    }
}
