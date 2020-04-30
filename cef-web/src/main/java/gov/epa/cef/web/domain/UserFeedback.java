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
    private Double intuitiveRating;

    @Column(name = "data_entry_via_screens")
    private Double dataEntryScreens;

    @Column(name = "calculation_screens")
    private Double calculationScreens;
    
    @Column(name = "controls_and_control_paths")
    private Double controlsAndControlPathAssignments;

    @Column(name = "overall_reporting_time")
    private Double overallReportingTime;

    @Column(name = "beneficial_functionality_description")
    private String beneficialFunctionalityComments;

    @Column(name = "difficult_application_functionality_description")
    private String difficultFunctionalityComments;
    
    @Column(name = "additional_features_or_enhancements_description")
    private String enhancementComments;
    
    @Column(name = "data_entry_via_bulk_upload")
    private Double dataEntryBulkUpload;

    @Column(name = "quality_assurance_checks")
    private Double qualityAssuranceChecks;
    
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

	public Double getCalculationScreens() {
		return calculationScreens;
	}

	public void setCalculationScreens(Double calculationScreens) {
		this.calculationScreens = calculationScreens;
	}

	public Double getControlsAndControlPathAssignments() {
		return controlsAndControlPathAssignments;
	}

	public void setControlsAndControlPathAssignments(Double controlsAndControlPathAssignments) {
		this.controlsAndControlPathAssignments = controlsAndControlPathAssignments;
	}

	public Double getOverallReportingTime() {
		return overallReportingTime;
	}

	public void setOverallReportingTime(Double overallReportingTime) {
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

	public Double getDataEntryBulkUpload() {
		return dataEntryBulkUpload;
	}

	public void setDataEntryBulkUpload(Double dataEntryBulkUpload) {
		this.dataEntryBulkUpload = dataEntryBulkUpload;
	}

	public Double getQualityAssuranceChecks() {
		return qualityAssuranceChecks;
	}

	public void setQualityAssuranceChecks(Double qualityAssuranceChecks) {
		this.qualityAssuranceChecks = qualityAssuranceChecks;
	}

}
