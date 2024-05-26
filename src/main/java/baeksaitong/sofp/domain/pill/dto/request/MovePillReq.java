package baeksaitong.sofp.domain.pill.dto.request;

import baeksaitong.sofp.global.util.EncryptionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovePillReq {
    @NotNull(message = "알약 시리얼 번호가 필요합니다.")
    @Schema(description = "알약 시리얼 번호")
    private Long pillSerialNumber;
    @NotNull(message = "카테고리 ID가 필요합니다.")
    @Schema(description = "카테고리 ID")
    private String categoryId;

    @JsonIgnore
    public Long getDecryptCategoryId(){
        return EncryptionUtil.decrypt(categoryId);
    }
}
