package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long reportingPeriodId;
    private String pollutantCode;
    private String pollutantName;
    private Integer totalEmissions;
    private CodeLookupDto emissionsUomCode;
    private BigDecimal emissionsFactor;
    private String emissionsFactorText;
    private CodeLookupDto emissionsCalcMethodCode;
    private String pollutantCasId;
    private String comments;
    private BigDecimal calculatedEmissionsTons;
    private CodeLookupDto emissionsNumeratorUom;
    private CodeLookupDto emissionsDenominatorUom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }

    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    public String getPollutantName() {
        return pollutantName;
    }

    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }

    public Integer getTotalEmissions() {
        return totalEmissions;
    }

    public void setTotalEmissions(Integer totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public CodeLookupDto getEmissionsUomCode() {
        return emissionsUomCode;
    }

    public void setEmissionsUomCode(CodeLookupDto emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    public BigDecimal getEmissionsFactor() {
        return emissionsFactor;
    }

    public void setEmissionsFactor(BigDecimal emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
    }

    public String getEmissionsFactorText() {
        return emissionsFactorText;
    }

    public void setEmissionsFactorText(String emissionsFactorText) {
        this.emissionsFactorText = emissionsFactorText;
    }

    public CodeLookupDto getEmissionsCalcMethodCode() {
        return emissionsCalcMethodCode;
    }

    public void setEmissionsCalcMethodCode(CodeLookupDto emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
    }

    public String getPollutantCasId() {
        return pollutantCasId;
    }

    public void setPollutantCasId(String pollutantCasId) {
        this.pollutantCasId = pollutantCasId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getCalculatedEmissionsTons() {
        return calculatedEmissionsTons;
    }

    public CodeLookupDto getEmissionsNumeratorUom() {
        return emissionsNumeratorUom;
    }

    public CodeLookupDto getEmissionsDenominatorUom() {
        return emissionsDenominatorUom;
    }

    public void setCalculatedEmissionsTons(BigDecimal calculatedEmissionsTons) {
        this.calculatedEmissionsTons = calculatedEmissionsTons;
    }

    public void setEmissionsNumeratorUom(CodeLookupDto emissionsNumeratorUom) {
        this.emissionsNumeratorUom = emissionsNumeratorUom;
    }

    public void setEmissionsDenominatorUom(CodeLookupDto emissionsDenominatorUom) {
        this.emissionsDenominatorUom = emissionsDenominatorUom;
    }

}
