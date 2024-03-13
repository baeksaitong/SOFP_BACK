package baeksaitong.sofp.domain.search.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ImageReq {
    private MultipartFile image;
    private String shape;
    private String sign;
    private String color;
    private String formulation;
    private String line;
    private int page=0;
    private int limit=5;
}
