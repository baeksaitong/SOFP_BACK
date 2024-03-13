package baeksaitong.sofp.domain.search.controller;

import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.service.SearchService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/keyword")
    public ResponseEntity<KeywordRes> findByKeyword(@RequestBody KeywordReq req){
        KeywordRes res = searchService.findByKeyword(req);
        return BaseResponse.ok(res);
    }

    @GetMapping("/info")
    public ResponseEntity<PillInfoRes> getPillDetailInfo(@RequestParam String serialNumber){
        PillInfoRes res = searchService.getPillInfo(serialNumber);
        return BaseResponse.ok(res);
    }

    @PostMapping("/image")
    public ResponseEntity<?> findByImage(@ModelAttribute ImageReq req){
        searchService.findByImage(req);
        return BaseResponse.ok("임시");
    }
}
