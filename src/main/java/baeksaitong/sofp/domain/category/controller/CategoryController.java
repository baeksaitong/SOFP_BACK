package baeksaitong.sofp.domain.category.controller;

import baeksaitong.sofp.domain.category.dto.request.CategoryEditReq;
import baeksaitong.sofp.domain.category.dto.request.CategoryReq;
import baeksaitong.sofp.domain.category.dto.response.CategoryDetailRes;
import baeksaitong.sofp.domain.category.dto.response.CategoryListByDayRes;
import baeksaitong.sofp.domain.category.dto.response.CategoryListByProfileRes;
import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.domain.category.service.CategoryService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83C\uDFF7️ Category")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "\uD83D\uDD11 카테고리 추가", description = "해당 프로필에 주어진 조건에 맞게 카테고리를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 추가에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다. <br>" +
                    "code: C-001 | message: 이미 존재하는 카테고리 이름입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{profileId}")
    public ResponseEntity<String> addCategory(
            @RequestBody @Validated CategoryReq req,
            @PathVariable @Schema(description = "프로필 ID") String profileId
    ){
        categoryService.addCategory(req, profileId);
        return BaseResponse.ok("카테고리 추가에 성공했습니다.");
    }

    @Operation(summary = "\uD83D\uDD11 카테고리 정보", description = "카테고리 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 정보"),
            @ApiResponse(responseCode = "404", description = "code: C-000 | message: 존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailRes> getCategoryInfo(
            @PathVariable @Schema(description = "카테고리 ID") String categoryId
    ){
        CategoryDetailRes res = categoryService.getCategoryInfo(categoryId);
        return BaseResponse.ok(res);
    }
    @Operation(summary = "\uD83D\uDD11 카테고리 수정", description = "카테고리 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정된 카테고리 정보"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다. <br>" +
                    "code: C-000 | message: 존재하지 않는 카테고리입니다. <br>" +
                    "code: C-001 | message: 이미 존재하는 카테고리 이름입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailRes> editCategory(
            @RequestBody @Validated CategoryEditReq req,
            @PathVariable @Schema(description = "카테고리 ID") String categoryId
    ){
        CategoryDetailRes res = categoryService.editCategory(categoryId, req);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 카테고리 삭제", description = "카테고리를 삭제합니다. <br>" +
            "- 카테고리에 속한 알약 존재 시 삭제 여부에 따라 삭제 혹은 유지합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 삭제에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "code: C-000 | message: 존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable @Schema(description = "카테고리 ID") String categoryId,
            @RequestParam @Schema(description = "카테고리에 속한 알약 존재 시 삭제 여부(true=모두 삭제, false=알약 유지)") Boolean isAllDelete
    ){
        categoryService.deleteCategory(categoryId, isAllDelete);
        return BaseResponse.ok("카테고리 삭제에 성공했습니다.");
    }

    @Operation(summary = "\uD83D\uDD11 프로필에 속한 카테고리 전체 조회", description = "프로필에 속한 모든 카테고리를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 정보(카테고리 ID, 카테고리 이름) 리스트"),
            @ApiResponse(responseCode = "404", description = "code: U-001 | message: 프로필이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<CategoryListByProfileRes> getCategoryListByProfile(
            @RequestParam @Schema(description = "프로필 ID") String profileId
    ){
        CategoryListByProfileRes res = categoryService.getCategoryListByProfile(profileId);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 날짜에 해당하는 카테고리 전체 조회", description = "주어진 날짜와 프로필 ID 리스트에 해당하는 모든 카테고리를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 정보(카테고리 ID, 카테고리 이름) 리스트")
    })
    @GetMapping("/{profileId}/{day}")
    public ResponseEntity<CategoryListByDayRes> getCategoryListByDay(
            @PathVariable @Schema(description = "프로필 ID") String profileId,
            @ValidEnum(enumClass = Day.class, ignoreCase=true, message = "잘못된 요일 입력입니다.")
            @PathVariable @Schema(description = "조회 요일", example = "MON,TUE,WED,THU,FRI,SAT,SUN") String day
    ){
        CategoryListByDayRes res = categoryService.getCategoryListByDay(profileId, day);
        return BaseResponse.ok(res);
    }
}
