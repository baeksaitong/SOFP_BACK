package baeksaitong.sofp.domain.pharmacy.dto.pharmacyInfo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@Setter
@JacksonXmlRootElement(localName = "response")
public class AroundPharmacy {
    private Header header;
    private Body body;

    public int getStatus(){
        return header.resultCode;
    }
    public int getNumOfRows(){return body.numOfRows;}
    public int getPageNo(){return body.pageNo;}
    public int getTotalCount(){return body.totalCount;}
    public List<AroundPharmacyInfo> getItemList(){
        return body.aroundPharmacyInfoList;
    }

    @Setter
    public static class Header {
        private int resultCode;
    }

    @Setter
    @Getter
    public static class Body {
        private List<AroundPharmacyInfo> aroundPharmacyInfoList;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        @JacksonXmlElementWrapper(localName = "items")
        @JacksonXmlProperty(localName = "item")
        public void setAroundPharmacyInfoList(List<AroundPharmacyInfo> aroundPharmacyInfoList) {
            this.aroundPharmacyInfoList = aroundPharmacyInfoList;
        }
    }
}
