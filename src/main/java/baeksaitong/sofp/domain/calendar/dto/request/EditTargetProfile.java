package baeksaitong.sofp.domain.calendar.dto.request;

import baeksaitong.sofp.global.common.validation.NonNullList;
import baeksaitong.sofp.global.util.EncryptionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditTargetProfile {
    @Schema(description = "달력에 표시에 추가할 프로필 ID 리스트(없을 시 빈 리스트 필요)")
    @NonNullList(message = "리스트는 null 이거나 null 값을 포함할 수 없습니다.")
    private List<String> addTargetProfileIdList = new ArrayList<>();
    @Schema(description = "달력에 표시에서 삭제할 프로필 ID 리스트(없을 시 빈 리스트 필요)")
    @NonNullList(message = "리스트는 null 이거나 null 값을 포함할 수 없습니다.")
    private List<String> deleteTargetProfileIdList = new ArrayList<>();

    @JsonIgnore
    public List<Long> getDecryptAdd(){
        return addTargetProfileIdList.stream().map(EncryptionUtil::decrypt).toList();
    }

    @JsonIgnore
    public List<Long> getDecryptDelete(){
        return deleteTargetProfileIdList.stream().map(EncryptionUtil::decrypt).toList();
    }
}
