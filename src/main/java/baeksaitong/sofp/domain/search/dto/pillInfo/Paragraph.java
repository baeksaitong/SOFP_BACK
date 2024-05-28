package baeksaitong.sofp.domain.search.dto.pillInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PARAGRAPH")
@Getter
@Schema(description = "Paragraph 태그")
public class Paragraph {
    @XmlValue
    private String description;
}