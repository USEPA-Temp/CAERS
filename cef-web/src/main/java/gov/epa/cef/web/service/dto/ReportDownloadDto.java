package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ahmahfou
 *
 */
public class ReportDownloadDto implements Serializable{

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    
    
    private Long reportId;
    private Long facilitySiteId;
	private Short reportYear;
	private String emissionsUnitId;
	private String emissionUnitDescription;
	private String processId;
	private String processDescription;
    private String pollutantName;
    private String emissionsUomCode;
    private BigDecimal totalEmissions;
    private String emissionsNumeratorUom;
    private String emissionsDenominatorUom;
    private BigDecimal emissionsFactor;
    private BigDecimal overallControlPercent;
    private String emissionsFactorText;
    private String emissionsComment;
    private String reportingPeriodType;
    private String throughputUom;
    private String throughputMaterial;
    private BigDecimal throughputValue;
	private String emissionsCalcMethod;
	private String fuelUseMaterial;
	private String fuelUseUom;
	private BigDecimal fuelUseValue;
	private String heatContentUom;
	private BigDecimal heatContentValue;
    private String lastModifiedBy;
	private String lastModifiedDate;
	private String calculationComment;
	
    public String getThroughputUom() {
		return throughputUom;
	}
	public void setThroughputUom(String throughputUom) {
		this.throughputUom = throughputUom;
	}
	public BigDecimal getThroughputValue() {
		return throughputValue;
	}
	public void setThroughputValue(BigDecimal throughputValue) {
		this.throughputValue = throughputValue;
	}
	
    public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
	public String getEmissionsComment() {
		return emissionsComment;
	}
	public void setEmissionsComment(String emissionsComment) {
		this.emissionsComment = emissionsComment;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getFacilitySiteId() {
		return facilitySiteId;
	}
	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}
	public Short getReportYear() {
		return reportYear;
	}
	public void setReportYear(Short reportYear) {
		this.reportYear = reportYear;
	}
	public String getEmissionsUnitId() {
		return emissionsUnitId;
	}
	public void setEmissionsUnitId(String emissionsUnitId) {
		this.emissionsUnitId = emissionsUnitId;
	}
	public String getEmissionUnitDescription() {
		return emissionUnitDescription;
	}
	public void setEmissionUnitDescription(String emissionUnitDescription) {
		this.emissionUnitDescription = emissionUnitDescription;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessDescription() {
		return processDescription;
	}
	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	public String getPollutantName() {
		return pollutantName;
	}
	public void setPollutantName(String pollutantName) {
		this.pollutantName = pollutantName;
	}
	public BigDecimal getTotalEmissions() {
		return totalEmissions;
	}
	public void setTotalEmissions(BigDecimal totalEmissions) {
		this.totalEmissions = totalEmissions;
	}
	public String getReportingPeriodType() {
		return reportingPeriodType;
	}
	public void setReportingPeriodType(String reportingPeriodType) {
		this.reportingPeriodType = reportingPeriodType;
	}
	public String getEmissionsUomCode() {
		return emissionsUomCode;
	}
	public void setEmissionsUomCode(String emissionsUomCode) {
		this.emissionsUomCode = emissionsUomCode;
	}
	public String getEmissionsCalcMethod() {
		return emissionsCalcMethod;
	}
	public void setEmissionsCalcMethod(String emissionsCalcMethod) {
		this.emissionsCalcMethod = emissionsCalcMethod;
	}
	public BigDecimal getOverallControlPercent() {
		return overallControlPercent;
	}
	public void setOverallControlPercent(BigDecimal overallControlPercent) {
		this.overallControlPercent = overallControlPercent;
	}
	public String getCalculationComment() {
		return calculationComment;
	}
	public void setCalculationComment(String calculationComment) {
		this.calculationComment = calculationComment;
	}
	public String getThroughputMaterial() {
		return throughputMaterial;
	}
	public void setThroughputMaterial(String throughputMaterial) {
		this.throughputMaterial = throughputMaterial;
	}
	public String getFuelUseMaterial() {
		return fuelUseMaterial;
	}
	public void setFuelUseMaterial(String fuelUseMaterial) {
		this.fuelUseMaterial = fuelUseMaterial;
	}
	public String getFuelUseUom() {
		return fuelUseUom;
	}
	public void setFuelUseUom(String fuelUseUom) {
		this.fuelUseUom = fuelUseUom;
	}
	public BigDecimal getFuelUseValue() {
		return fuelUseValue;
	}
	public void setFuelUseValue(BigDecimal fuelUseValue) {
		this.fuelUseValue = fuelUseValue;
	}
	public String getHeatContentUom() {
		return heatContentUom;
	}
	public void setHeatContentUom(String heatContentUom) {
		this.heatContentUom = heatContentUom;
	}
	public BigDecimal getHeatContentValue() {
		return heatContentValue;
	}
	public void setHeatContentValue(BigDecimal heatContentValue) {
		this.heatContentValue = heatContentValue;
	}

}
