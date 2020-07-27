package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportingPeriodBulkEntryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // unit
    private Long emissionsUnitId;
    private String unitIdentifier;
    private String unitDescription;

    // process
    private Long emissionsProcessId;
    private String emissionsProcessIdentifier;
    private String emissionsProcessDescription;
    private CodeLookupDto operatingStatusCode;

    // reporting period
    private Long reportingPeriodId;
    private CodeLookupDto reportingPeriodTypeCode;
    private BigDecimal calculationParameterValue;
    private UnitMeasureCodeDto calculationParameterUom;
    private CodeLookupDto calculationMaterialCode;
    private BigDecimal previousCalculationParameterValue;
    private String previousCalculationParameterUomCode;

    public Long getEmissionsUnitId() {
        return emissionsUnitId;
    }

    public void setEmissionsUnitId(Long emissionsUnitId) {
        this.emissionsUnitId = emissionsUnitId;
    }

    public String getUnitIdentifier() {
        return unitIdentifier;
    }

    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public Long getEmissionsProcessId() {
        return emissionsProcessId;
    }

    public void setEmissionsProcessId(Long emissionsProcessid) {
        this.emissionsProcessId = emissionsProcessid;
    }

    public String getEmissionsProcessIdentifier() {
        return emissionsProcessIdentifier;
    }

    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    public String getEmissionsProcessDescription() {
        return emissionsProcessDescription;
    }

    public void setEmissionsProcessDescription(String processDescription) {
        this.emissionsProcessDescription = processDescription;
    }

    public CodeLookupDto getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(CodeLookupDto operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }

    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    public CodeLookupDto getReportingPeriodTypeCode() {
        return reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(CodeLookupDto reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public BigDecimal getCalculationParameterValue() {
        return calculationParameterValue;
    }

    public void setCalculationParameterValue(BigDecimal calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    public UnitMeasureCodeDto getCalculationParameterUom() {
        return calculationParameterUom;
    }

    public void setCalculationParameterUom(UnitMeasureCodeDto calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    public CodeLookupDto getCalculationMaterialCode() {
        return calculationMaterialCode;
    }

    public void setCalculationMaterialCode(CodeLookupDto calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public BigDecimal getPreviousCalculationParameterValue() {
        return previousCalculationParameterValue;
    }

    public void setPreviousCalculationParameterValue(BigDecimal previousCalculationParameterValue) {
        this.previousCalculationParameterValue = previousCalculationParameterValue;
    }

    public String getPreviousCalculationParameterUomCode() {
        return previousCalculationParameterUomCode;
    }

    public void setPreviousCalculationParameterUomCode(String previousCalculationParameterUom) {
        this.previousCalculationParameterUomCode = previousCalculationParameterUom;
    }

}
