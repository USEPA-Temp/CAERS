package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EmissionsReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emissions_report", schema = "public")

public class EmissionsReport implements java.io.Serializable {

    // Fields

    private Long id;
    private String frsFacilityId;
    private String eisProgramId;
    private String agencyCode;
    private Short year;
    private String status;
    private String validationStatus;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;

    // Constructors

    /** default constructor */
    public EmissionsReport() {
    }

    /** minimal constructor */
    public EmissionsReport(Long id, String frsFacilityId, String eisProgramId, String agencyCode, Short year,
            String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.frsFacilityId = frsFacilityId;
        this.eisProgramId = eisProgramId;
        this.agencyCode = agencyCode;
        this.year = year;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public EmissionsReport(Long id, String frsFacilityId, String eisProgramId, String agencyCode, Short year,
            String status, String validationStatus, String createdBy, Timestamp createdDate, String lastModifiedBy,
            Timestamp lastModifiedDate) {
        this.id = id;
        this.frsFacilityId = frsFacilityId;
        this.eisProgramId = eisProgramId;
        this.agencyCode = agencyCode;
        this.year = year;
        this.status = status;
        this.validationStatus = validationStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Property accessors
    @Id

    @Column(name = "id", unique = true, nullable = false)

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "frs_facility_id", nullable = false, length = 22)

    public String getFrsFacilityId() {
        return this.frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    @Column(name = "eis_program_id", nullable = false, length = 22)

    public String getEisProgramId() {
        return this.eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    @Column(name = "agency_code", nullable = false, length = 3)

    public String getAgencyCode() {
        return this.agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    @Column(name = "year", nullable = false)

    public Short getYear() {
        return this.year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    @Column(name = "status")

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "validation_status")

    public String getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    @Column(name = "created_by", nullable = false)

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_date", nullable = false, length = 29)

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_modified_by", nullable = false)

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "last_modified_date", nullable = false, length = 29)

    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}