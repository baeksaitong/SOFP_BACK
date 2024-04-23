package baeksaitong.sofp.domain.search.dto.pillInfo;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DOC")
@Getter
@ToString
public class Doc {
    @XmlAttribute(name = "title")
    private String title;

    @XmlElement(name = "SECTION")
    private List<Section> sectionList;
}
