package baeksaitong.sofp.domain.verification.controller;

import baeksaitong.sofp.domain.verification.dto.request.CheckEmailCodeReq;
import baeksaitong.sofp.domain.verification.dto.request.SendEmailCodeReq;
import baeksaitong.sofp.domain.verification.service.VerificationService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/mail/send")
    public ResponseEntity<String> sendEmailCode(@RequestBody @Validated SendEmailCodeReq req){
        verificationService.sendEmailCode(req.getEmail());
        return BaseResponse.ok("인증 코드를 발송하였습니다.");
    }

    @PostMapping("/mail/check")
    public ResponseEntity<Boolean> checkEmailCode(@RequestBody @Validated CheckEmailCodeReq req){
        boolean verified = verificationService.checkEmailCode(req.getEmail(), req.getCode());
        return BaseResponse.ok(verified);
    }
}
