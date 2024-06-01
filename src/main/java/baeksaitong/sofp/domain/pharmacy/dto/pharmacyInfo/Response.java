package baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "response")
public class Response {
    private Body body;

    @Data
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

    }

    @Data
    public static class Items {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<Item> itemList;

    }

    @Data
    public static class Item {
        private int cnt;
        private double distance;
        private String dutyAddr;
        private String dutyDiv;
        private String dutyDivName;
        private String dutyName;
        private String dutyTel1;
        private String endTime;
        private String hpid;
        private double latitude;
        private double longitude;
        private int rnum;
        private String startTime;

    }
}
