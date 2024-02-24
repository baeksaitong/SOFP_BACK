package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
}
