package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * EmissionsReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emissions_report", schema = "public")
public class EmissionsReport extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "frs_facility_id", nullable = false, length = 22)
    private String frsFacilityId;

    @Column(name = "eis_program_id", nullable = false, length = 22)
    private String eisProgramId;

    @Column(name = "agency_code", nullable = false, length = 3)
    private String agencyCode;

    @Column(name = "year", nullable = false)
    private Short year;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;

    @Column(name = "cromerr_activity_id", length = 37)
    private String cromerrActivityId;

    @Column(name = "cromerr_document_id", length = 36)
    private String cromerrDocumentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status")
    private ValidationStatus validationStatus;
    
    @Column(name = "has_submitted")
    private Boolean hasSubmitted;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsReport")
    private List<FacilitySite> facilitySites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsReport")
    private List<ReportHistory> reportHistory = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsReport")
    private List<ReportAttachment> reportAttachments = new ArrayList<>();
    
    /***
     * Default constructor
     */
    public EmissionsReport() {}


    /***
     * Copy constructor
     * @param originalEmissionsReport The emissions report object being copied
     */
    public EmissionsReport(EmissionsReport originalEmissionsReport) {
		this.id = originalEmissionsReport.getId();
    	this.frsFacilityId = originalEmissionsReport.frsFacilityId;
    	this.eisProgramId = originalEmissionsReport.eisProgramId;
    	this.agencyCode = originalEmissionsReport.agencyCode;
    	this.year = originalEmissionsReport.year;
    	this.status = originalEmissionsReport.status;
    	this.validationStatus = originalEmissionsReport.validationStatus;
    	for (FacilitySite facilitySite : originalEmissionsReport.facilitySites) {
    		this.facilitySites.add(new FacilitySite(this, facilitySite));
    	}
    }


    // Property accessors

    public String getFrsFacilityId() {
        return this.frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }
    
    public Boolean getHasSubmitted() {
		return hasSubmitted;
	}

	public void setHasSubmitted(Boolean hasSubmitted) {
		this.hasSubmitted = hasSubmitted;
	}

    public String getEisProgramId() {
        return this.eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    public String getAgencyCode() {
        return this.agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Short getYear() {
        return this.year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public ReportStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public List<FacilitySite> getFacilitySites() {
        return this.facilitySites;
    }

    public void setFacilitySites(Collection<FacilitySite> facilitySites) {

        this.facilitySites.clear();
        if (facilitySites != null) {
            this.facilitySites.addAll(facilitySites);
        }
    }
    
    public List<ReportHistory> getReportHistory() {
      	return this.reportHistory;
    }
    
    public void setReportHistory(List<ReportHistory> reportHistory) {
    	
    	this.reportHistory.clear();
    	if (reportHistory != null) {
    		this.reportHistory.addAll(reportHistory);
    	}
    }
    
    public List<ReportAttachment> getReportAttachments() {
      	return this.reportAttachments;
    }
    
    public void setReportAttachments(List<ReportAttachment> reportAttachments) {
    	
    	this.reportAttachments.clear();
    	if (reportAttachments != null) {
    		this.reportAttachments.addAll(reportAttachments);
    	}
    }

    public String getCromerrActivityId() {
        return cromerrActivityId;
    }

    public void setCromerrActivityId(String cromerrActivityId) {
        this.cromerrActivityId = cromerrActivityId;
    }

    public String getCromerrDocumentId() {
        return cromerrDocumentId;
    }

    public void setCromerrDocumentId(String cromerrDocumentId) {
        this.cromerrDocumentId = cromerrDocumentId;
    }


    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    	for (FacilitySite facilitySite : this.facilitySites) {
    		facilitySite.clearId();
    	}
    }
}
