package baeksaitong.sofp.domain.search.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageReq {
    @Schema(description = "검색 이미지")
    @NotNull(message = "이미지가 필요합니다.")
    private MultipartFile image;
    @Schema(description = "알약 모양")
    private String shape;
    @Schema(description = "알약에 적힌 기호")
    private String sign;
    @Schema(description = "알약 색깔")
    private String color;
    @Schema(description = "알약 제형")
    private String formulation;
    @Schema(description = "알약 분할선")
    private String line;
    @Schema(description = "검색 페이지 - 0부터 시작, 기본값 : 0")
    private Integer page;
    @Schema(description = "페이지 당 요소 갯수 - 기본값 : 5")
    private Integer limit;

    public ImageReq(MultipartFile image, String shape, String sign, String color, String formulation, String line, Integer page, Integer limit) {
        this.image = image;
        this.shape = shape;
        this.sign = sign;
        this.color = color;
        this.formulation = formulation;
        this.line = line;
        this.page = (page != null) ? page : 0;
        this.limit = (limit != null) ? limit : 5;
    }
}
