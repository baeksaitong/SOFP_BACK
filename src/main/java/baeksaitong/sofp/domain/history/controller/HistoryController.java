package baeksaitong.sofp.domain.history.controller;

import baeksaitong.sofp.domain.history.dto.request.HistoryDeleteReq;
import baeksaitong.sofp.domain.history.dto.request.HistoryReq;
import baeksaitong.sofp.domain.history.dto.response.HistoryRes;
import baeksaitong.sofp.domain.history.service.HistoryService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/recent-view")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping
    public ResponseEntity<HistoryRes> getRecentViewPill(
            @RequestBody HistoryReq req,
            @RequestParam Long profileId
    ){
        HistoryRes res = historyService.getRecentViewPill(req, profileId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/delete")
    public ResponseEntity<HistoryRes> deleteRecentViewPill(
            @RequestBody HistoryDeleteReq req,
            @RequestParam Long profileId
            ){
        HistoryRes res =  historyService.deleteRecentViewPill(req, profileId);
        return BaseResponse.ok(res);
    }
}
