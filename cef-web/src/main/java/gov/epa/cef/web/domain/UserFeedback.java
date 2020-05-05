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
    private String reportId;
    
    @Column(name = "easy_and_intuitive")
    private Integer intuitiveRating;

    @Column(name = "data_entry_via_screens")
    private Integer dataEntryScreens;

    @Column(name = "calculation_screens")
    private Integer calculationScreens;
    
    @Column(name = "controls_and_control_paths")
    private Integer controlsAndControlPathAssignments;

    @Column(name = "overall_reporting_time")
    private Integer overallReportingTime;

    @Column(name = "beneficial_functionality_description")
    private String beneficialFunctionalityComments;

    @Column(name = "difficult_application_functionality_description")
    private String difficultFunctionalityComments;
    
    @Column(name = "additional_features_or_enhancements_description")
    private String enhancementComments;
    
    @Column(name = "data_entry_via_bulk_upload")
    private Integer dataEntryBulkUpload;

    @Column(name = "quality_assurance_checks")
    private Integer qualityAssuranceChecks;
    
	/***
     * Default constructor
     */
    public UserFeedback() {}
    
    public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
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

	public Integer getOverallReportingTime() {
		return overallReportingTime;
	}

	public void setOverallReportingTime(Integer overallReportingTime) {
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

	public Integer getDataEntryBulkUpload() {
		return dataEntryBulkUpload;
	}

	public void setDataEntryBulkUpload(Integer dataEntryBulkUpload) {
		this.dataEntryBulkUpload = dataEntryBulkUpload;
	}

	public Integer getQualityAssuranceChecks() {
		return qualityAssuranceChecks;
	}

	public void setQualityAssuranceChecks(Integer qualityAssuranceChecks) {
		this.qualityAssuranceChecks = qualityAssuranceChecks;
	}
    
}
