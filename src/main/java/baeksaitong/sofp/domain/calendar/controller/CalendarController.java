package baeksaitong.sofp.domain.calendar.controller;

import baeksaitong.sofp.domain.calendar.dto.request.EditTargetProfile;
import baeksaitong.sofp.domain.calendar.dto.response.TargetProfileRes;
import baeksaitong.sofp.domain.calendar.service.CalendarService;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83D\uDCC5 Calendar")
@RestController
@RequestMapping("/app/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(summary = "\uD83D\uDD11 달력 표시 프로필 조회", description = "주어진 프로필의 달력에 표시되는 프로필들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "달력에 표시되는 프로필 ID 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{profileId}")
    public ResponseEntity<TargetProfileRes> getTargetProfile(
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        TargetProfileRes res = calendarService.getTargetProfile(profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 달력 표시 프로필 수정", description = "주어진 프로필의 달력에 표시될 프로필을 추가하거나 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "달력 표시 프로필 수정에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{profileId}")
    public ResponseEntity<String> editTargetProfile(
            @PathVariable @Schema(description = "프로필 ID") String profileId,
            @RequestBody @Validated EditTargetProfile req
    ){
        calendarService.editTargetProfile(req, profileId);
        return BaseResponse.ok("달력 표시 프로필 수정에 성공했습니다.");
    }

}
