package baeksaitong.sofp.domain.search.dto.pillInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ARTICLE")
@Getter
@Schema(description = "Article 태그")
public class Article {
    @XmlAttribute(name = "title")
    @Schema(description = "빈칸 일 수 있습니다.")
    private String title;

    @XmlElement(name = "PARAGRAPH")
    private List<Paragraph> paragraphList;
}
