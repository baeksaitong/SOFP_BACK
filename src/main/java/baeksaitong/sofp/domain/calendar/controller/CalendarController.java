package baeksaitong.sofp.domain.calendar.controller;

import baeksaitong.sofp.domain.calendar.dto.request.EditTargetProfile;
import baeksaitong.sofp.domain.calendar.dto.response.TargetProfileRes;
import baeksaitong.sofp.domain.calendar.service.CalendarService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/{profileId}")
    public ResponseEntity<TargetProfileRes> getTargetProfile(
            @PathVariable Long profileId
    ){
        TargetProfileRes res = calendarService.getTargetProfile(profileId);
        return BaseResponse.ok(res);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<String> editTargetProfile(
            @PathVariable Long profileId,
            @RequestBody EditTargetProfile req
    ){
        calendarService.editTargetProfile(req, profileId);
        return BaseResponse.ok("달력 표시 프로필 수정에 성공했습니다.");
    }

}
