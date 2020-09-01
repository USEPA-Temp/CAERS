package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "user_feedback")

public class UserFeedback extends BaseAuditEntity {
    
	private static final long serialVersionUID = 1L;

    // Fields
    
    @Column(name = "report_id")
    private Long reportId;
    
    @Column(name = "facility_name")
    private String facilityName;
    
    @Column(name = "easy_and_intuitive")
    private Long intuitiveRating;

    @Column(name = "data_entry_via_screens")
    private Long dataEntryScreens;

    @Column(name = "calculation_screens")
    private Long calculationScreens;
    
    @Column(name = "controls_and_control_paths")
    private Long controlsAndControlPathAssignments;

    @Column(name = "overall_reporting_time")
    private Long overallReportingTime;

    @Column(name = "beneficial_functionality_description")
    private String beneficialFunctionalityComments;

    @Column(name = "difficult_application_functionality_description")
    private String difficultFunctionalityComments;
    
    @Column(name = "additional_features_or_enhancements_description")
    private String enhancementComments;
    
    @Column(name = "data_entry_via_bulk_upload")
    private Long dataEntryBulkUpload;

    @Column(name = "quality_assurance_checks")
    private Long qualityAssuranceChecks;
    
    @Column(name = "agency_code", nullable = false, length = 3)
    private String agencyCode; 
    
    @Column(name = "year")
    private Short year;
    
    @Column(name = "user_name")
    private String userName;
    
	/***
     * Default constructor
     */
    public UserFeedback() {}
    
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

	public Long getOverallReportingTime() {
		return overallReportingTime;
	}

	public void setOverallReportingTime(Long overallReportingTime) {
		this.overallReportingTime = overallReportingTime;
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

	public Long getDataEntryBulkUpload() {
		return dataEntryBulkUpload;
	}

	public void setDataEntryBulkUpload(Long dataEntryBulkUpload) {
		this.dataEntryBulkUpload = dataEntryBulkUpload;
	}

	public Long getQualityAssuranceChecks() {
		return qualityAssuranceChecks;
	}

	public void setQualityAssuranceChecks(Long qualityAssuranceChecks) {
		this.qualityAssuranceChecks = qualityAssuranceChecks;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public Short getYear() {
		return year;
	}

	public void setYear(Short year) {
		this.year = year;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
    
}
