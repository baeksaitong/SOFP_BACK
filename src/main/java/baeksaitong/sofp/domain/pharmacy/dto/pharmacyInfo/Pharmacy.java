package baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Setter;

import java.util.List;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pharmacy {
    private Header header;
    private Body body;

    public Boolean getStatus(){
        return (header.resultCode == 0) && (body.pharmacyInfoList != null) && (!body.pharmacyInfoList.isEmpty());
    }

    public PharmacyInfo getItemList(){
        return body.pharmacyInfoList.get(0);
    }
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Header {
        private int resultCode;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private List<PharmacyInfo> pharmacyInfoList;

        @JacksonXmlElementWrapper(localName = "items")
        @JacksonXmlProperty(localName = "item")
        public void setPharmacyInfoList(List<PharmacyInfo> pharmacyInfoList) {
            this.pharmacyInfoList = pharmacyInfoList;
        }
    }
}
