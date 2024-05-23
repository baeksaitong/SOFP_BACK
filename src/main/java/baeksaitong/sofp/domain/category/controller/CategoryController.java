package baeksaitong.sofp.domain.category.controller;

import baeksaitong.sofp.domain.category.dto.request.CategoryEditReq;
import baeksaitong.sofp.domain.category.dto.request.CategoryListByDay;
import baeksaitong.sofp.domain.category.dto.request.CategoryReq;
import baeksaitong.sofp.domain.category.dto.response.CategoryDetailRes;
import baeksaitong.sofp.domain.category.dto.response.CategoryListByDayRes;
import baeksaitong.sofp.domain.category.dto.response.CategoryListByProfileRes;
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

    @GetMapping("/info")
    public ResponseEntity<CategoryDetailRes> getCategoryInfo(@RequestParam Long categoryId){
        CategoryDetailRes res = categoryService.getCategoryInfo(categoryId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/edit")
    public ResponseEntity<CategoryDetailRes> editCategory(
            @RequestBody CategoryEditReq req,
            @RequestParam Long categoryId,
            @RequestParam Long profileId
    ){
        CategoryDetailRes res = categoryService.editCategory(categoryId, profileId, req);
        return BaseResponse.ok(res);
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteCategory(
            @RequestParam Long categoryId,
            @RequestParam Boolean isAllDelete
    ){
        categoryService.deleteCategory(categoryId, isAllDelete);
        return BaseResponse.ok("카테고리 삭제에 성공했습니다.");
    }

    @GetMapping("/all")
    public ResponseEntity<CategoryListByProfileRes> getCategoryListByProfile(@RequestParam Long profileId){
        CategoryListByProfileRes res = categoryService.getCategoryListByProfile(profileId);
        return BaseResponse.ok(res);
    }

    @PostMapping("/all")
    public ResponseEntity<CategoryListByDayRes> getCategoryListByDay(@RequestBody CategoryListByDay req){
        CategoryListByDayRes res = categoryService.getCategoryListByDay(req);
        return BaseResponse.ok(res);
    }
}
