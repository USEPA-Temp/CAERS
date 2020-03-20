package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Emission ID is required.")
    private Long id;

    @NotNull(message = "Report Period is required.")
    private Long reportingPeriodId;

    @NotBlank(message = "Pollutant Code is required.")
    @Size(max = 12, message = "Pollutant Code can not exceed {max} chars; found '${validatedValue}'.")
    private String pollutantCode;

    private boolean totalManualEntry;

    @Digits(integer = 2, fraction = 6,
        message = "Overall Control Percent is not in expected numeric format: '{integer}.{fraction}' digits.")
    private BigDecimal overallControlPercent;

    @NotNull(message = "Total Emissions is required.")
    private BigDecimal totalEmissions;

    @NotBlank(message = "Emissions UoM Code is required.")
    @Size(max = 20, message = "Emissions UoM Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsUomCode;

    private BigDecimal emissionsFactor;

    @Size(max = 100, message = "Emissions Factor Formula can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsFactorFormula;

    @Size(max = 100, message = "Emissions Factor Text can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsFactorText;

    @NotBlank(message = "Emissions Calculation Method is required.")
    @Size(max = 20, message = "Emissions Calculation Method can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsCalcMethodCode;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @Size(max = 4000, message = "Description of Calculation can not exceed {max} chars; found '${validatedValue}'.")
    private String calculationComment;

    @Size(max = 20, message = "Emissions Numerator UoM Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsNumeratorUom;

    @Size(max = 20, message = "Emissions Denominator UoM Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsDenominatorUom;

    private BigDecimal calculatedEmissionsTons;

    public EmissionBulkUploadDto() {

        super(WorksheetName.Emission);
    }

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

    public boolean isTotalManualEntry() {
        return totalManualEntry;
    }
    public void setTotalManualEntry(boolean totalManualEntry) {
        this.totalManualEntry = totalManualEntry;
    }

    public BigDecimal getOverallControlPercent() {
        return overallControlPercent;
    }
    public void setOverallControlPercent(BigDecimal overallControlPercent) {
        this.overallControlPercent = overallControlPercent;
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

    public String getCalculationComment() {
        return calculationComment;
    }
    public void setCalculationComment(String calculationComment) {
        this.calculationComment = calculationComment;
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

    public String getEmissionsFactorFormula() {

        return emissionsFactorFormula;
    }

    public void setEmissionsFactorFormula(String emissionsFactorFormula) {

        this.emissionsFactorFormula = emissionsFactorFormula;
    }
}
