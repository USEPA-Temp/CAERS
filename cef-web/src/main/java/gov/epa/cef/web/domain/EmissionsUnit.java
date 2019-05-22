package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EmissionsUnit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emissions_unit")

public class EmissionsUnit implements java.io.Serializable {

    // Fields

    private Long id;
    private UnitTypeCode unitTypeCode;
    private OperatingStatusCode operatingStatusCode;
    private Facility facility;
    private String unitIdentifier;
    private String programSystemCode;
    private String description;
    private String typeCodeDescription;
    private Short statusYear;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private Set<EmissionsProcess> emissionsProcesses = new HashSet<EmissionsProcess>(0);

    // Constructors

    /** default constructor */
    public EmissionsUnit() {
    }

    /** minimal constructor */
    public EmissionsUnit(Long id, UnitTypeCode unitTypeCode, OperatingStatusCode operatingStatusCode, Facility facility,
            String unitIdentifier, String programSystemCode, String typeCodeDescription, Short statusYear,
            String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.unitTypeCode = unitTypeCode;
        this.operatingStatusCode = operatingStatusCode;
        this.facility = facility;
        this.unitIdentifier = unitIdentifier;
        this.programSystemCode = programSystemCode;
        this.typeCodeDescription = typeCodeDescription;
        this.statusYear = statusYear;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public EmissionsUnit(Long id, UnitTypeCode unitTypeCode, OperatingStatusCode operatingStatusCode, Facility facility,
            String unitIdentifier, String programSystemCode, String description, String typeCodeDescription,
            Short statusYear, String createdBy, Timestamp createdDate, String lastModifiedBy,
            Timestamp lastModifiedDate, Set<EmissionsProcess> emissionsProcesses) {
        this.id = id;
        this.unitTypeCode = unitTypeCode;
        this.operatingStatusCode = operatingStatusCode;
        this.facility = facility;
        this.unitIdentifier = unitIdentifier;
        this.programSystemCode = programSystemCode;
        this.description = description;
        this.typeCodeDescription = typeCodeDescription;
        this.statusYear = statusYear;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.emissionsProcesses = emissionsProcesses;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code", nullable = false)

    public UnitTypeCode getUnitTypeCode() {
        return this.unitTypeCode;
    }

    public void setUnitTypeCode(UnitTypeCode unitTypeCode) {
        this.unitTypeCode = unitTypeCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)

    public OperatingStatusCode getOperatingStatusCode() {
        return this.operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)

    public Facility getFacility() {
        return this.facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Column(name = "unit_identifier", nullable = false, length = 20)

    public String getUnitIdentifier() {
        return this.unitIdentifier;
    }

    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    @Column(name = "program_system_code", nullable = false, length = 20)

    public String getProgramSystemCode() {
        return this.programSystemCode;
    }

    public void setProgramSystemCode(String programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    @Column(name = "description", length = 100)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "type_code_description", nullable = false, length = 100)

    public String getTypeCodeDescription() {
        return this.typeCodeDescription;
    }

    public void setTypeCodeDescription(String typeCodeDescription) {
        this.typeCodeDescription = typeCodeDescription;
    }

    @Column(name = "status_year", nullable = false)

    public Short getStatusYear() {
        return this.statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsUnit")

    public Set<EmissionsProcess> getEmissionsProcesses() {
        return this.emissionsProcesses;
    }

    public void setEmissionsProcesses(Set<EmissionsProcess> emissionsProcesses) {
        this.emissionsProcesses = emissionsProcesses;
    }

}