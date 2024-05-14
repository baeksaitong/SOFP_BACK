package baeksaitong.sofp.domain.search.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ImageReq {
    @Schema(description = "검색 이미지")
    @NotNull(message = "이미지가 필요합니다.")
    private List<MultipartFile> images;
    @Schema(description = "검색 페이지 - 0부터 시작, 기본값 : 0")
    private Integer page;
    @Schema(description = "페이지 당 요소 갯수 - 기본값 : 5")
    private Integer limit;

    public ImageReq(List<MultipartFile> images, Integer page, Integer limit) {
        this.images = images;
        this.page = (page != null) ? page : 0;
        this.limit = (limit != null) ? limit : 5;
    }
}
