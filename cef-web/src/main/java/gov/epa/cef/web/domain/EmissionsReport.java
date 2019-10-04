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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * EmissionsReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emissions_report", schema = "public")
public class EmissionsReport extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Column(name = "frs_facility_id", nullable = false, length = 22)
    private String frsFacilityId;

    @NotBlank
    @Column(name = "eis_program_id", nullable = false, length = 22)
    private String eisProgramId;

    @NotBlank
    @Column(name = "agency_code", nullable = false, length = 3)
    private String agencyCode;

    @NotNull
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsReport")
    private Set<FacilitySite> facilitySites = new HashSet<FacilitySite>(0);

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

    public Set<FacilitySite> getFacilitySites() {
        return this.facilitySites;
    }

    public void setFacilitySites(Set<FacilitySite> facilitySites) {
        this.facilitySites = facilitySites;
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
