package baeksaitong.sofp.domain.pill.controller;

import baeksaitong.sofp.domain.pill.dto.request.PillReq;
import baeksaitong.sofp.domain.pill.dto.response.PillRes;
import baeksaitong.sofp.domain.pill.service.PillService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/pill")
@RequiredArgsConstructor
public class PillController {
    private final PillService pillService;

    @GetMapping
    public ResponseEntity<PillRes> getPillList(
            @RequestParam String name, @AuthenticationPrincipal Member member
    ){
        PillRes res = pillService.getPillList(name, member);
        return BaseResponse.ok(res);
    }

    @PostMapping("/add")
    public ResponseEntity<PillRes> addPill(
            @RequestParam String name, @RequestBody PillReq req, @AuthenticationPrincipal Member member
    ){
        PillRes res = pillService.addPill(name, req, member);
        return BaseResponse.ok(res);
    }

    @PostMapping("/delete")
    public ResponseEntity<PillRes> removePill(
            @RequestParam String name, @RequestBody PillReq req, @AuthenticationPrincipal Member member
    ){
        PillRes res = pillService.removePill(name, req, member);
        return BaseResponse.ok(res);
    }
}
