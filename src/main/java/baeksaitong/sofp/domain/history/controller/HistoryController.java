package baeksaitong.sofp.domain.history.controller;

import baeksaitong.sofp.domain.history.dto.request.HistoryDeleteReq;
import baeksaitong.sofp.domain.history.dto.request.HistoryReq;
import baeksaitong.sofp.domain.history.dto.response.HistoryRes;
import baeksaitong.sofp.domain.history.service.HistoryService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/recent-view")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping
    public ResponseEntity<HistoryRes> getRecentViewPill(
            @RequestBody HistoryReq req,
            @AuthenticationPrincipal Member member
    ){
        HistoryRes res = historyService.getRecentViewPill(req, member);
        return BaseResponse.ok(res);
    }

    @PostMapping("/delete")
    public ResponseEntity<HistoryRes> deleteRecentViewPill(
            @RequestBody HistoryDeleteReq req,
            @AuthenticationPrincipal Member member
            ){
        HistoryRes res =  historyService.deleteRecentViewPill(req, member);
        return BaseResponse.ok(res);
    }
}
