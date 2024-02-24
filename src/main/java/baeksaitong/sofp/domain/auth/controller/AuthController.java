package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.dto.request.CheckIdReq;
import baeksaitong.sofp.domain.auth.dto.request.SignUpReq;
import baeksaitong.sofp.domain.auth.service.AuthService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpReq req){
        authService.singUp(req);
        return BaseResponse.ok("회원 가입에 성공했습니다.");
    }

    @PostMapping("/id-check")
    public ResponseEntity<String> checkId(@RequestBody CheckIdReq req){
        authService.checkId(req);
        return BaseResponse.ok("사용가능한 아이디입니다.");
    }
}
