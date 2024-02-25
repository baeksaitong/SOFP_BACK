package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.dto.request.CheckIdReq;
import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.auth.service.AuthService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(tags = "1. Auth", summary = "회원 가입", description = "회원 가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "code: A-000 | message: 이미 존재하는 아이디입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpReq req){
        authService.singUp(req);
        return BaseResponse.ok("회원 가입에 성공했습니다.");
    }

    @Operation(tags = "1. Auth", summary = "아이디 중복 검사", description = "아이디 중복 여부를 검사합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용가능 한 아이디입니다."),
            @ApiResponse(responseCode = "404", description = "code: A-000 | message: 이미 존재하는 아이디입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/id-check")
    public ResponseEntity<String> checkId(@RequestBody CheckIdReq req){
        authService.checkId(req);
        return BaseResponse.ok("사용가능 한 아이디입니다.");
    }
}
