package baeksaitong.sofp.domain.search.dto.pillInfo;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ARTICLE")
@Getter
@ToString
public class Article {
    @XmlAttribute(name = "title")
    private String title;

    @XmlElement(name = "PARAGRAPH")
    private List<Paragraph> paragraphList;
}
