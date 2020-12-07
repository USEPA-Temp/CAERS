package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ReportingPeriodDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long emissionsProcessId;
    private CodeLookupDto reportingPeriodTypeCode;
    private CodeLookupDto emissionsOperatingTypeCode;
    private CodeLookupDto calculationParameterTypeCode;
    private BigDecimal calculationParameterValue;
    private UnitMeasureCodeDto calculationParameterUom;
    private CodeLookupDto calculationMaterialCode;
    private BigDecimal fuelUseValue;
    private UnitMeasureCodeDto fuelUseValueUom;
    private CodeLookupDto fuelUseValueMaterialCode;
    private String comments;
    private List<EmissionDto> emissions;
    private List<OperatingDetailDto> operatingDetails;

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

    public CodeLookupDto getReportingPeriodTypeCode() {
        return reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(CodeLookupDto reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public CodeLookupDto getEmissionsOperatingTypeCode() {
        return emissionsOperatingTypeCode;
    }

    public void setEmissionsOperatingTypeCode(CodeLookupDto emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    public CodeLookupDto getCalculationParameterTypeCode() {
        return calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(CodeLookupDto calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
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

    public BigDecimal getFuelUseValue() {
		return fuelUseValue;
	}

	public void setFuelUseValue(BigDecimal fuelUseValue) {
		this.fuelUseValue = fuelUseValue;
	}

	public UnitMeasureCodeDto getFuelUseValueUom() {
		return fuelUseValueUom;
	}

	public void setFuelUseValueUom(UnitMeasureCodeDto fuelUseValueUom) {
		this.fuelUseValueUom = fuelUseValueUom;
	}

	public CodeLookupDto getFuelUseValueMaterialCode() {
		return fuelUseValueMaterialCode;
	}

	public void setFuelUseValueMaterialCode(
			CodeLookupDto fuelUseValueMaterialCode) {
		this.fuelUseValueMaterialCode = fuelUseValueMaterialCode;
	}

	public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<EmissionDto> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<EmissionDto> emissions) {
        this.emissions = emissions;
    }

    public List<OperatingDetailDto> getOperatingDetails() {
        return operatingDetails;
    }

    public void setOperatingDetails(List<OperatingDetailDto> operatingDetails) {
        this.operatingDetails = operatingDetails;
    }

    public ReportingPeriodDto withId(Long id) {

        setId(id);
        return this;
    }
}
