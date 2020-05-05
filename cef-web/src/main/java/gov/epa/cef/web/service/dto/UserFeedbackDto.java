package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class UserFeedbackDto implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String beneficialFunctionalityComments;
    private String difficultFunctionalityComments;
    private String enhancementComments;
    private Integer reportId;
    private Integer intuitiveRating;
    private Integer dataEntryScreens;
    private Integer dataEntryBulkUpload;
    private Integer calculationScreens;
    private Integer controlsAndControlPathAssignments;
    private Integer qualityAssuranceChecks;
    private Integer overallReportingTime;
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
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Integer getIntuitiveRating() {
		return intuitiveRating;
	}
	public void setIntuitiveRating(Integer intuitiveRating) {
		this.intuitiveRating = intuitiveRating;
	}
	public Integer getDataEntryScreens() {
		return dataEntryScreens;
	}
	public void setDataEntryScreens(Integer dataEntryScreens) {
		this.dataEntryScreens = dataEntryScreens;
	}
	public Integer getDataEntryBulkUpload() {
		return dataEntryBulkUpload;
	}
	public void setDataEntryBulkUpload(Integer dataEntryBulkUpload) {
		this.dataEntryBulkUpload = dataEntryBulkUpload;
	}
	public Integer getCalculationScreens() {
		return calculationScreens;
	}
	public void setCalculationScreens(Integer calculationScreens) {
		this.calculationScreens = calculationScreens;
	}
	public Integer getControlsAndControlPathAssignments() {
		return controlsAndControlPathAssignments;
	}
	public void setControlsAndControlPathAssignments(Integer controlsAndControlPathAssignments) {
		this.controlsAndControlPathAssignments = controlsAndControlPathAssignments;
	}
	public Integer getQualityAssuranceChecks() {
		return qualityAssuranceChecks;
	}
	public void setQualityAssuranceChecks(Integer qualityAssuranceChecks) {
		this.qualityAssuranceChecks = qualityAssuranceChecks;
	}
	public Integer getOverallReportingTime() {
		return overallReportingTime;
	}
	public void setOverallReportingTime(Integer overallReportingTime) {
		this.overallReportingTime = overallReportingTime;
	}
       
}
