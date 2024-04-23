package baeksaitong.sofp.domain.search.dto.pillInfo;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SECTION")
@Getter
@ToString
public class Section {
    @XmlAttribute(name = "title")
    private String title;

    @XmlElement(name = "ARTICLE")
    private List<Article> articleList;
}