package baeksaitong.sofp.domain.search.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PillInfoRes {
    @Schema(description = "알약 이름")
    private String name;
    @Schema(description = "제조 회사")
    private String enterpriseName;
    @Schema(description = "일반/전문 의약품 정보")
    private String proOrGeneral;
    @Schema(description = "유통 기한")
    private String permitDate;
    @Schema(description = "알약 성상")
    private String chart;
    @Schema(description = "알약 재료")
    private String material;
    @Schema(description = "알약 저장 방법")
    private String storageMethod;
    @Schema(description = "알약 유통 기한")
    private String validTerm;
    @Schema(description = "효능효과 문서 데이터")
    private String efficacyEffect;
    @Schema(description = "용법용량 문서 데이터")
    private String dosageUsage;
    @Schema(description = "주의사항(일반) 문서 데이터")
    private String cautionGeneral;
    @Schema(description = "주의사항(전문) 문서 데이터")
    private String cautionProfessional;

    @Schema(description = "내 정보와 관련된 경고 사항")
    private List<String> waringInfo;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public PillInfoRes(@JsonProperty("body") Map<String,Object> body){
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


    public void setWaringInfo(List<String> waringInfo){
        this.waringInfo = waringInfo;
    }

    public void setEfficacyEffect(String efficacyEffect) {
        this.efficacyEffect = efficacyEffect;
    }

    public void setDosageUsage(String dosageUsage) {
        this.dosageUsage = dosageUsage;
    }

    public void setCautionGeneral(String cautionGeneral) {
        this.cautionGeneral = cautionGeneral;
    }
}
