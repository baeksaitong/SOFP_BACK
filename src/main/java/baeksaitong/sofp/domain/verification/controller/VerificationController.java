package baeksaitong.sofp.domain.verification.controller;

import baeksaitong.sofp.domain.verification.dto.request.CheckEmailCodeReq;
import baeksaitong.sofp.domain.verification.dto.request.SendEmailCodeReq;
import baeksaitong.sofp.domain.verification.service.VerificationService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "\uD83D\uDCE7 Verification")
@RestController
@RequestMapping("/app/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @Operation(summary = "메일 인증 코드 전송", description = "메일 인증을 위한 코드를 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 코드를 발송하였습니다."),
            @ApiResponse(responseCode = "500", description = "code: M-000 | message: 메일 전송에 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/mail/send")
    public ResponseEntity<String> sendEmailCode(@RequestBody @Validated SendEmailCodeReq req){
        verificationService.sendEmailCode(req.getEmail());
        return BaseResponse.ok("인증 코드를 발송하였습니다.");
    }

    @Operation(summary = "메일 코드 인증",
            description = "메일 인증 코드 일치 여부를 검사합니다.<br>" +
                    "- '회원가입' API 수행하기 전 해당 API를 통해 인증을 완료해야 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 일치 시 True 반환"),
            @ApiResponse(responseCode = "500", description = "code: M-000 | message: 메일 전송에 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "code: M-001 | message: 코드 유효기간이 만료 되었습니다.<br>"+
                    "code: M-002 | message: 코드가 일치하지 않습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/mail/check")
    public ResponseEntity<Boolean> checkEmailCode(@RequestBody @Validated CheckEmailCodeReq req){
        boolean verified = verificationService.checkEmailCode(req.getEmail(), req.getCode());
        return BaseResponse.ok(verified);
    }
}
