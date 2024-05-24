package baeksaitong.sofp.domain.calendar.controller;

import baeksaitong.sofp.domain.calendar.dto.response.TargetRes;
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
    public ResponseEntity<TargetRes> getTarget(
            @PathVariable Long profileId
    ){
        TargetRes res = calendarService.getTarget(profileId);
        return BaseResponse.ok(res);
    }
}
