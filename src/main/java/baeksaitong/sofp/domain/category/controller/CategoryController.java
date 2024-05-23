package baeksaitong.sofp.domain.category.controller;

import baeksaitong.sofp.domain.category.dto.request.CategoryReq;
import baeksaitong.sofp.domain.category.service.CategoryService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(
            @RequestBody CategoryReq req,
            @RequestParam Long profileId
    ){
        categoryService.addCategory(req, profileId);
        return BaseResponse.ok("추가 완료");
    }
}
