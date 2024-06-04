package baeksaitong.sofp.domain.pharmacy.controller;

import baeksaitong.sofp.domain.pharmacy.dto.response.AroundPharmacyRes;
import baeksaitong.sofp.domain.pharmacy.dto.response.PharmacyRes;
import baeksaitong.sofp.domain.pharmacy.service.PharmacyService;
import baeksaitong.sofp.global.common.dto.BaseResponse;
import baeksaitong.sofp.global.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "\uD83C\uDFE5 Pharmacy")
@RestController
@RequestMapping("/app/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Operation(summary = "\uD83D\uDD11 주변 약국 조회", description = "일정 반경 내의 주변 약국을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주변 약국 리스트 정보 제공"),
            @ApiResponse(responseCode = "500", description = "code : PH-000 | message : 약국 정보를 불려오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/around")
    public ResponseEntity<AroundPharmacyRes> getAroundPharmacy(
            @RequestParam @Schema(description = "경도") Double longitude,
            @RequestParam @Schema(description = "위도") Double latitude,
            @RequestParam(required = false, defaultValue = "2.0") @Schema(description = "검색 반경 - 양의 실수", defaultValue = "2.0") @Min(0) Double distance){
        AroundPharmacyRes res = pharmacyService.getAroundPharmacy(longitude, latitude, distance);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 야간 약국 조회", description = "일정 반경 내의 18시 이후 영업하는 야간 약국을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주변 야간 약국 리스트 정보 제공"),
            @ApiResponse(responseCode = "500", description = "code : PH-000 | message : 약국 정보를 불려오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/night")
    public ResponseEntity<AroundPharmacyRes> getNightPharmacy(
            @RequestParam @Schema(description = "경도") Double longitude,
            @RequestParam @Schema(description = "위도") Double latitude,
            @RequestParam(required = false, defaultValue = "2.0") @Schema(description = "검색 반경 - 양의 실수", defaultValue = "2.0") @Min(0) Double distance){
        AroundPharmacyRes res = pharmacyService.getNightPharmacy(longitude, latitude, distance);
        return BaseResponse.ok(res);
    }

    @Operation(summary = "\uD83D\uDD11 약국 조회", description = "약국 정보을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "약국 정보"),
            @ApiResponse(responseCode = "500", description = "code : PH-000 | message : 약국 정보를 불려오는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{pharmacyId}")
    public ResponseEntity<PharmacyRes> getPharmacy(
            @PathVariable @Schema(description = "약국 ID") String pharmacyId
    ){
        PharmacyRes res = pharmacyService.getPharmacy(pharmacyId);
        return BaseResponse.ok(res);
    }
}
