package gov.epa.cef.web.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * EmissionsReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emissions_report", schema = "public")
public class EmissionsReport extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields
    
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
    
    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status")
    private ValidationStatus validationStatus;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsReport")
    private Set<FacilitySite> facilitySites = new HashSet<FacilitySite>(0);

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
}