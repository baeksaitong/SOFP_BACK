package baeksaitong.sofp.domain.category.dto.request;

import baeksaitong.sofp.domain.category.entity.enums.Day;
import baeksaitong.sofp.global.common.validation.NonNullList;
import baeksaitong.sofp.global.common.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryListByDay {
    @Schema(description = "조회 할 프로필 ID 리스트(없을 시 빈 배열 필요)")
    @NonNullList(message = "프로필 ID 리스트는 null 이거나 null 값을 포함할 수 없습니다.")
    private List<Long> profileIdList;
    @Schema(description = "조회 요일", example = "MON,TUE,WED,THU,FRI,SAT,SUN")
    @NotNull(message = "요일 정보가 필요합니다.")
    @ValidEnum(enumClass = Day.class, ignoreCase=true, message = "잘못된 요일 입력입니다.")
    private String day;
}
