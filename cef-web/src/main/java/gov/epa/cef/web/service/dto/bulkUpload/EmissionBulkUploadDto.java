package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionBulkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long reportingPeriodId;
    private String pollutantCode;
    private BigDecimal totalEmissions;
    private String emissionsUomCode;
    private BigDecimal emissionsFactor;
    private String emissionsFactorText;
    private String emissionsCalcMethodCode;
    private String comments;
    private String emissionsNumeratorUom;
    private String emissionsDenominatorUom;
    private BigDecimal calculatedEmissionsTons;

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

    public BigDecimal getTotalEmissions() {
        return totalEmissions;
    }
    public void setTotalEmissions(BigDecimal totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public String getEmissionsUomCode() {
        return emissionsUomCode;
    }
    public void setEmissionsUomCode(String emissionsUomCode) {
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

    public String getEmissionsCalcMethodCode() {
        return emissionsCalcMethodCode;
    }
    public void setEmissionsCalcMethodCode(String emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
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
    public void setCalculatedEmissionsTons(BigDecimal calculatedEmissionsTons) {
        this.calculatedEmissionsTons = calculatedEmissionsTons;
    }

    public String getEmissionsNumeratorUom() {
        return emissionsNumeratorUom;
    }
    public void setEmissionsNumeratorUom(String emissionsNumeratorUom) {
        this.emissionsNumeratorUom = emissionsNumeratorUom;
    }

    public String getEmissionsDenominatorUom() {
        return emissionsDenominatorUom;
    }
    public void setEmissionsDenominatorUom(String emissionsDenominatorUom) {
        this.emissionsDenominatorUom = emissionsDenominatorUom;
    }
}
