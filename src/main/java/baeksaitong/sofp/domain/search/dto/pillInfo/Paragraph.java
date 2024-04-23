package baeksaitong.sofp.domain.search.dto.pillInfo;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.ToString;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PARAGRAPH")
@Getter
@ToString
public class Paragraph {
    @XmlValue
    private String description;
}