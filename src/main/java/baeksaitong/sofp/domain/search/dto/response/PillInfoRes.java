package baeksaitong.sofp.domain.search.dto.response;

import baeksaitong.sofp.domain.search.dto.pillInfo.Doc;
import baeksaitong.sofp.domain.search.dto.pillInfo.PillInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

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
    private Doc efficacyEffect;
    @Schema(description = "용법용량 문서 데이터")
    private Doc dosageUsage;
    @Schema(description = "주의사항(일반) 문서 데이터")
    private Doc cautionGeneral;
    @Schema(description = "주의사항(전문) 문서 데이터")
    private Doc cautionProfessional;

    @Schema(description = "내 정보와 관련된 경고 사항")
    private List<String> waringInfo;

    public PillInfoRes(PillInfoDto pillInfoDto, Doc efficacyEffect, Doc dosageUsage, Doc cautionGeneral, Doc cautionProfessional, List<String> allWaring){
        this.name = pillInfoDto.getName();
        this.enterpriseName = pillInfoDto.getEnterpriseName();
        this.proOrGeneral = pillInfoDto.getProOrGeneral();
        this.permitDate = pillInfoDto.getPermitDate();
        this.chart = pillInfoDto.getChart();
        this.material = pillInfoDto.getMaterial();
        this.storageMethod = pillInfoDto.getStorageMethod();
        this.validTerm = pillInfoDto.getValidTerm();
        this.efficacyEffect = efficacyEffect;
        this.dosageUsage = dosageUsage;
        this.cautionGeneral = cautionGeneral;
        this.cautionProfessional = cautionProfessional;
        this.waringInfo = allWaring;
    }
}
