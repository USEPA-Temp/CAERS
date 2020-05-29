package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class UserFeedbackDto implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String beneficialFunctionalityComments;
    private String difficultFunctionalityComments;
    private String enhancementComments;
    private String facilityName;
    private Short year;
	private Long reportId;
    private Long intuitiveRating;
    private Long dataEntryScreens;
    private Long dataEntryBulkUpload;
    private Long calculationScreens;
    private Long controlsAndControlPathAssignments;
    private Long qualityAssuranceChecks;
    private Long overallReportingTime;
    private String userName;
    private String userId;
    private String userRole;
    
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public Short getYear() {
		return year;
	}
	public void setYear(Short year) {
		this.year = year;
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
	
       
}
