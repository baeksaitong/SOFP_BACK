package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.dto.request.CheckEmailCodeReq;
import baeksaitong.sofp.domain.auth.dto.request.SendEmailCodeReq;
import baeksaitong.sofp.domain.auth.service.AuthService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/mail/send")
    public ResponseEntity<String> sendEmailCode(@RequestBody @Validated SendEmailCodeReq req){
        authService.sendEmailCode(req.getEmail());
        return BaseResponse.ok("인증 코드를 발송하였습니다.");
    }

    @PostMapping("/mail/check")
    public ResponseEntity<Boolean> checkEmailCode(@RequestBody @Validated CheckEmailCodeReq req){
        boolean verified = authService.checkEmailCode(req.getEmail(), req.getCode());
        return BaseResponse.ok(verified);
    }
}
