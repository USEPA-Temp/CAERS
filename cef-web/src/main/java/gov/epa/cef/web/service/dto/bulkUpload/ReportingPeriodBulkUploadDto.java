package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportingPeriodBulkUploadDto implements IWorksheetAware, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long emissionsProcessId;
    private String reportingPeriodTypeCode;
    private String emissionsOperatingTypeCode;
    private String calculationParameterTypeCode;
    private BigDecimal calculationParameterValue;
    private String calculationParameterUom;
    private String calculationMaterialCode;
    private String comments;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmissionsProcessId() {
        return emissionsProcessId;
    }
    public void setEmissionsProcessId(Long emissionsProcessId) {
        this.emissionsProcessId = emissionsProcessId;
    }

    public String getReportingPeriodTypeCode() {
        return reportingPeriodTypeCode;
    }
    public void setReportingPeriodTypeCode(String reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public String getEmissionsOperatingTypeCode() {
        return emissionsOperatingTypeCode;
    }
    public void setEmissionsOperatingTypeCode(String emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    public String getCalculationParameterTypeCode() {
        return calculationParameterTypeCode;
    }
    public void setCalculationParameterTypeCode(String calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public BigDecimal getCalculationParameterValue() {
        return calculationParameterValue;
    }
    public void setCalculationParameterValue(BigDecimal calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    public String getCalculationParameterUom() {
        return calculationParameterUom;
    }
    public void setCalculationParameterUom(String calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    public String getCalculationMaterialCode() {
        return calculationMaterialCode;
    }
    public void setCalculationMaterialCode(String calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
