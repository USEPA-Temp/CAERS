package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class UserFeedbackDto implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String beneficialFunctionalityComments;
    private String difficultFunctionalityComments;
    private String enhancementComments;
    private String reportId;
    private Double intuitiveRating;
    private Double dataEntryScreens;
    private Double dataEntryBulkUpload;
    private Double calculationScreens;
    private Double controlsAndControlPathAssignments;
    private Double qualityAssuranceChecks;
    private Double overallReportingTime;
    
	public String getBeneficialFunctionalityComments() {
		return beneficialFunctionalityComments;
	}
	public void setBeneficialFunctionalityComments(String beneficialFunctionalityComments) {
		this.beneficialFunctionalityComments = beneficialFunctionalityComments;
	}
	public String getDifficultFunctionalityComments() {
		return difficultFunctionalityComments;
	}
	public void setDifficultFunctionalityComments(String difficultFunctionalityComments) {
		this.difficultFunctionalityComments = difficultFunctionalityComments;
	}
	public String getEnhancementComments() {
		return enhancementComments;
	}
	public void setEnhancementComments(String enhancementComments) {
		this.enhancementComments = enhancementComments;
	}
	public Double getIntuitiveRating() {
		return intuitiveRating;
	}
	public void setIntuitiveRating(Double intuitiveRating) {
		this.intuitiveRating = intuitiveRating;
	}
	public Double getDataEntryScreens() {
		return dataEntryScreens;
	}
	public void setDataEntryScreens(Double dataEntryScreens) {
		this.dataEntryScreens = dataEntryScreens;
	}
	public Double getDataEntryBulkUpload() {
		return dataEntryBulkUpload;
	}
	public void setDataEntryBulkUpload(Double dataEntryBulkUpload) {
		this.dataEntryBulkUpload = dataEntryBulkUpload;
	}
	public Double getCalculationScreens() {
		return calculationScreens;
	}
	public void setCalculationScreens(Double calculationScreens) {
		this.calculationScreens = calculationScreens;
	}
	public Double getQualityAssuranceChecks() {
		return qualityAssuranceChecks;
	}
	public void setQualityAssuranceChecks(Double qualityAssuranceChecks) {
		this.qualityAssuranceChecks = qualityAssuranceChecks;
	}
	public Double getControlsAndControlPathAssignments() {
		return controlsAndControlPathAssignments;
	}
	public void setControlsAndControlPathAssignments(Double controlsAndControlPathAssignments) {
		this.controlsAndControlPathAssignments = controlsAndControlPathAssignments;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public Double getOverallReportingTime() {
		return overallReportingTime;
	}
	public void setOverallReportingTime(Double overallReportingTime) {
		this.overallReportingTime = overallReportingTime;
	}
    
}
