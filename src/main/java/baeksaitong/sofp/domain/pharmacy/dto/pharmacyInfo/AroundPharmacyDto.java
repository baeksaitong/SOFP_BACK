package baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "response")
public class AroundPharmacyDto {
    private Header header;
    private Body body;

    public int getStatus(){
        return header.resultCode;
    }

    public List<Item> getItemList(){
        return body.items.itemList;
    }

    @Setter
    @NoArgsConstructor
    public static class Header {
        private int resultCode;
    }
    @Setter
    @NoArgsConstructor
    public static class Body {
        protected Items items;
    }
    @Setter
    @NoArgsConstructor
    public static class Items {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<Item> itemList;

    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Item {
        private int cnt;
        private double distance;
        private String dutyAddr;
        private String dutyDiv;
        private String dutyDivName;
        private String dutyFax;
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
