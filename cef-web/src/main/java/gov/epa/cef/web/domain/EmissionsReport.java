package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "validation_status")
    private String validationStatus;

    // Constructors

    /** default constructor */
    public EmissionsReport() {
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }
}