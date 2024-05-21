package baeksaitong.sofp.domain.pill.controller;

import baeksaitong.sofp.domain.pill.dto.request.PillReq;
import baeksaitong.sofp.domain.pill.dto.response.PillRes;
import baeksaitong.sofp.domain.pill.service.PillService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/pill")
@RequiredArgsConstructor
public class PillController {
    private final PillService pillService;

    @GetMapping
    public ResponseEntity<PillRes> getPillList(@RequestParam Long profileId){
        PillRes res = pillService.getPillList(profileId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/add")
    public ResponseEntity<PillRes> addPill(@RequestBody PillReq req,@RequestParam Long profileId){
        PillRes res = pillService.addPill(req, profileId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/delete")
    public ResponseEntity<PillRes> removePill(@RequestBody PillReq req, @RequestParam Long profileId){
        PillRes res = pillService.removePill(req, profileId);
        return BaseResponse.ok(res);
    }
}
