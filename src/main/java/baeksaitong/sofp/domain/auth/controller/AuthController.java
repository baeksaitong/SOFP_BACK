package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.dto.request.CheckIdReq;
import baeksaitong.sofp.domain.auth.dto.request.LoginReq;
import baeksaitong.sofp.domain.auth.dto.request.RefreshTokenReq;
import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.dto.response.RefreshAccessRes;
import baeksaitong.sofp.domain.auth.dto.response.TokenRes;
import baeksaitong.sofp.domain.auth.service.AuthService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83D\uDD10 Auth")
@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원 가입", description = "회원 가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신규 회원 여부=true, access 토큰"),
            @ApiResponse(responseCode = "404", description = "code: A-000 | message: 이미 존재하는 아이디입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/sign-up")
    public ResponseEntity<LoginRes> signUp(@RequestBody @Validated SignUpReq req){
        return BaseResponse.ok(authService.singUp(req));
    }

    @Operation(summary = "아이디 중복 검사", description = "아이디 중복 여부를 검사합니다.")
            @ApiResponses({
                    @ApiResponse(responseCode = "200", description = "사용가능 한 아이디입니다."),
                    @ApiResponse(responseCode = "404", description = "code: A-000 | message: 이미 존재하는 아이디입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/id-check")
    public ResponseEntity<String> checkId(@RequestBody @Validated CheckIdReq req){
        authService.checkId(req);
        return BaseResponse.ok("사용가능 한 아이디입니다.");
    }

    @Operation(summary = "로그인", description = "신규 회원 여부=false, 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access 토큰"),
            @ApiResponse(responseCode = "404", description = "code: A-001 | message: 존재하지 않는 아이디입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "code: A-002 | message: 비밀번호가 일치하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody @Validated LoginReq req){
        return BaseResponse.ok(new LoginRes(false, authService.login(req)));
    }

    @Operation(summary = "Access Token 갱신", description = "refresh 토큰을 통해 access 토큰을 재발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access 토큰"),
            @ApiResponse(responseCode = "404", description = "code: J-000 | message: 유효하지 않은 jwt 토큰입니다. <br>" +
                    "code: J-001 | message: 만료된 jwt 토큰입니다. <br>" +
                    "code: J-002 | message: 지원하지 않는 JWT 토큰입니다.<br>" +
                    "code: J-003 | message: 토큰이 필요합니다.<br>" +
                    "code: J-004 | message: 토큰을 갱신할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh/access")
    public ResponseEntity<RefreshAccessRes> refreshAccess(@RequestBody @Validated RefreshTokenReq req) {
        RefreshAccessRes res = authService.refreshAccessToken0(req);
        return BaseResponse.ok(res);
    }

    @Operation( summary = "Access 및 Refresh Token 갱신", description = "refresh 토큰을 통해 access 및 refresh 토큰을 재발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access, refresh 토큰"),
            @ApiResponse(responseCode = "404", description = "code: J-000 | message: 유효하지 않은 jwt 토큰입니다. <br>" +
                    "code: J-001 | message: 만료된 jwt 토큰입니다. <br>" +
                    "code: J-002 | message: 지원하지 않는 JWT 토큰입니다.<br>" +
                    "code: J-003 | message: 토큰이 필요합니다.<br>" +
                    "code: J-004 | message: 토큰을 갱신할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<TokenRes> refresh(@RequestBody @Validated RefreshTokenReq req) {
        TokenRes res = authService.refresh(req);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "토큰 만료기한 조회", description = "유효한 토큰인 경우, 해당 토큰의 만료기한을 조회할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰의 만료기한 (yyyy-MM-dd HH:mm:ss)"),
            @ApiResponse(responseCode = "400", description = "code: J-000 | message: 유효하지 않은 jwt 토큰입니다. <br>" +
                    "code: J-001 | message: 만료된 jwt 토큰입니다. <br>" +
                    "code: J-002 | message: 지원하지 않는 JWT 토큰입니다.<br>" +
                    "code: J-003 | message: 토큰이 필요합니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/expiration")
    public ResponseEntity<String> getExpiration(@RequestParam @Validated @NotBlank String token) {
        String expiration = authService.getExpiration(token);
        return BaseResponse.ok(expiration);
    }

}
