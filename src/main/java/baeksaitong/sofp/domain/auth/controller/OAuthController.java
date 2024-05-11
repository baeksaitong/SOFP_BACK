package baeksaitong.sofp.domain.auth.controller;

import baeksaitong.sofp.domain.auth.dto.response.LoginRes;
import baeksaitong.sofp.domain.auth.service.KakaoService;
import baeksaitong.sofp.domain.auth.service.NaverService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "\uD83D\uDD10 OAuth")
@RestController
@RequestMapping("/app/oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @Operation(summary = "카카오 로그인", description = "카카오 인가코드를 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access 토큰"),
            @ApiResponse(responseCode = "500", description = "code: A-003 | message: 카카오 유저 정보를 받아오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/kakao")
    public ResponseEntity<LoginRes> kakao(String code){
        return BaseResponse.ok(kakaoService.login(code));
    }

    @Operation(summary = "네이버 로그인", description = "네이버 인가코드를 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access 토큰"),
            @ApiResponse(responseCode = "500", description = "code: A-004 | message: 네이버 유저 정보를 받아오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/naver")
    public ResponseEntity<LoginRes> naver(String code){
        return BaseResponse.ok(naverService.login(code));
    }
}
