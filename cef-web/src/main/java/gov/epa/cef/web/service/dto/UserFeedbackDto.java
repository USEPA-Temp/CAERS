package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class UserFeedbackDto implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
	private Long id;
    private String beneficialFunctionalityComments;
    private String difficultFunctionalityComments;
    private String enhancementComments;
    private Long reportId;
    private Long intuitiveRating;
    private Long dataEntryScreens;
    private Long dataEntryBulkUpload;
    private Long calculationScreens;
    private Long controlsAndControlPathAssignments;
    private Long qualityAssuranceChecks;
    private Long overallReportingTime;
    private Boolean hasSubmitted;
    private Boolean hasVisitedPage;
    
    public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}
    
	public Boolean getHasVisitedPage() {
		return hasVisitedPage;
	}
	public void setHasVisitedPage(Boolean hasVisitedPage) {
		this.hasVisitedPage = hasVisitedPage;
	}
	public Boolean getHasSubmitted() {
		return hasSubmitted;
	}
	public void setHasSubmitted(Boolean hasSubmitted) {
		this.hasSubmitted = hasSubmitted;
	}
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
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getIntuitiveRating() {
		return intuitiveRating;
	}
	public void setIntuitiveRating(Long intuitiveRating) {
		this.intuitiveRating = intuitiveRating;
	}
	public Long getDataEntryScreens() {
		return dataEntryScreens;
	}
	public void setDataEntryScreens(Long dataEntryScreens) {
		this.dataEntryScreens = dataEntryScreens;
	}
	public Long getDataEntryBulkUpload() {
		return dataEntryBulkUpload;
	}
	public void setDataEntryBulkUpload(Long dataEntryBulkUpload) {
		this.dataEntryBulkUpload = dataEntryBulkUpload;
	}
	public Long getCalculationScreens() {
		return calculationScreens;
	}
	public void setCalculationScreens(Long calculationScreens) {
		this.calculationScreens = calculationScreens;
	}
	public Long getControlsAndControlPathAssignments() {
		return controlsAndControlPathAssignments;
	}
	public void setControlsAndControlPathAssignments(Long controlsAndControlPathAssignments) {
		this.controlsAndControlPathAssignments = controlsAndControlPathAssignments;
	}
	public Long getQualityAssuranceChecks() {
		return qualityAssuranceChecks;
	}
	public void setQualityAssuranceChecks(Long qualityAssuranceChecks) {
		this.qualityAssuranceChecks = qualityAssuranceChecks;
	}
	public Long getOverallReportingTime() {
		return overallReportingTime;
	}
	public void setOverallReportingTime(Long overallReportingTime) {
		this.overallReportingTime = overallReportingTime;
	}
	public UserFeedbackDto withId(Long reportId) {
        setReportId(reportId);
        return this;
    }
       
}
