package gov.epa.cef.web.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;


/**
 * EmissionsUnit entity
 */
@Entity
@Table(name = "emissions_unit")
public class EmissionsUnit extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code", nullable = false)
    private UnitTypeCode unitTypeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)
    private OperatingStatusCode operatingStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;

    @Column(name = "unit_identifier", nullable = false, length = 20)
    private String unitIdentifier;

    @Column(name = "program_system_code", nullable = false, length = 20)
    private String programSystemCode;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "type_code_description", nullable = false, length = 100)
    private String typeCodeDescription;

    @Column(name = "status_year", nullable = false)
    private Short statusYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_measure_cd")
    private UnitMeasureCode unitOfMeasureCode;
    
    @Column(name = "comments", length = 200)
    private String comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsUnit")
    private Set<EmissionsProcess> emissionsProcesses = new HashSet<EmissionsProcess>(0);

    /** default constructor */
    public EmissionsUnit() {
    }


    public UnitTypeCode getUnitTypeCode() {
        return this.unitTypeCode;
    }
    public void setUnitTypeCode(UnitTypeCode unitTypeCode) {
        this.unitTypeCode = unitTypeCode;
    }

    public OperatingStatusCode getOperatingStatusCode() {
        return this.operatingStatusCode;
    }
    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public FacilitySite getFacilitySite() {
        return this.facilitySite;
    }
    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public String getUnitIdentifier() {
        return this.unitIdentifier;
    }
    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    public String getProgramSystemCode() {
        return this.programSystemCode;
    }
    public void setProgramSystemCode(String programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeCodeDescription() {
        return this.typeCodeDescription;
    }
    public void setTypeCodeDescription(String typeCodeDescription) {
        this.typeCodeDescription = typeCodeDescription;
    }

    public Short getStatusYear() {
        return this.statusYear;
    }
    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public UnitMeasureCode getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }
    public void setUnitOfMeasureCode(UnitMeasureCode unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }


    public Set<EmissionsProcess> getEmissionsProcesses() {
        return this.emissionsProcesses;
    }
    public void setEmissionsProcesses(Set<EmissionsProcess> emissionsProcesses) {
        this.emissionsProcesses = emissionsProcesses;
    }

}