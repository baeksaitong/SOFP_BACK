package baeksaitong.sofp.domain.calendar.controller;

import baeksaitong.sofp.domain.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
}
