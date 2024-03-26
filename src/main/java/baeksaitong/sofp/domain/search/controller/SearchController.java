package baeksaitong.sofp.domain.search.controller;

import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.service.SearchService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/keyword")
    public ResponseEntity<KeywordRes> findByKeyword(@RequestBody KeywordReq req, @AuthenticationPrincipal Member member){
        KeywordRes res = searchService.findByKeyword(req, member);
        return BaseResponse.ok(res);
    }

    @GetMapping("/info")
    public ResponseEntity<PillInfoRes> getPillDetailInfo(@RequestParam String serialNumber, @AuthenticationPrincipal Member member){
        PillInfoRes res = searchService.getPillInfo(serialNumber, member);
        return BaseResponse.ok(res);
    }

    @PostMapping("/image")
    public ResponseEntity<?> findByImage(@ModelAttribute ImageReq req){
        searchService.findByImage(req);
        return BaseResponse.ok("임시");
    }
}
