package baeksaitong.sofp.domain.search.dto.pillInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PillInfoDto {
    private String name;
    private String enterpriseName;
    private String proOrGeneral;
    private String permitDate;
    private String chart;
    private String material;
    private String storageMethod;
    private String validTerm;
    private String efficacyEffect;
    private String dosageUsage;
    private String cautionGeneral;
    private String cautionProfessional;

    @JsonCreator
    public PillInfoDto(@JsonProperty("body") Map<String,Object> body){
       if((Integer)body.get("totalCount") > 0){
           Map<String,Object> result = (Map<String, Object>)((List<Object>) body.get("items")).get(0);
           this.name = (String) result.get("ITEM_NAME");
           this.enterpriseName = (String) result.get("ENTP_NAME");
           this.proOrGeneral = (String) result.get("ETC_OTC_CODE");
           this.permitDate = (String) result.get("ITEM_PERMIT_DATE");
           this.chart = (String) result.get("CHART");
           this.material = (String) result.get("MATERIAL_NAME");
           this.validTerm = (String) result.get("VALID_TERM");
           this.storageMethod = (String) result.get("STORAGE_METHOD");
           this.efficacyEffect = (String) result.get("EE_DOC_DATA");
           this.dosageUsage = (String) result.get("UD_DOC_DATA");
           this.cautionGeneral = (String) result.get("NB_DOC_DATA");
           this.cautionProfessional = (String) result.get("PN_DOC_DATA");
       }
    }
}
