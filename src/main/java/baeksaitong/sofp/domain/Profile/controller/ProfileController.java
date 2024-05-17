package baeksaitong.sofp.domain.Profile.controller;

import baeksaitong.sofp.domain.Profile.dto.request.ProfileReq;
import baeksaitong.sofp.domain.Profile.dto.response.BasicInfoRes;
import baeksaitong.sofp.domain.Profile.service.ProfileService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
}
