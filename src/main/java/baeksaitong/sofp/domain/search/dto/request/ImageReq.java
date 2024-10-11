package baeksaitong.sofp.domain.search.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageReq {
    @Schema(description = "프로필 ID")
    String profileId;
    @Schema(description = "검색 이미지")
    @NotNull(message = "이미지가 필요합니다.")
    private List<MultipartFile> images;
    @Schema(description = "추가 요청시 이전 결과의 마지막 알약 요소 ID")
    String lastId;
    @Schema(description = "페이지 당 요소 갯수 - 기본값 : 10")
    private Integer limit = 10;

}
