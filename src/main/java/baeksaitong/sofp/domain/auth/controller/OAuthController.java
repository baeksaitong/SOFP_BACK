package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
}
