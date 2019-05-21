package gov.epa.cef.web.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * OperatingStatusCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operating_status_code", schema = "public")

public class OperatingStatusCode implements java.io.Serializable {

    // Fields

    private String code;
    private String description;
    private Set<ReleasePoint> releasePoints = new HashSet<ReleasePoint>(0);
    private Set<EmissionsUnit> emissionsUnits = new HashSet<EmissionsUnit>(0);
    private Set<Facility> facilities = new HashSet<Facility>(0);
    private Set<EmissionsProcess> emissionsProcesses = new HashSet<EmissionsProcess>(0);

    // Constructors

    /** default constructor */
    public OperatingStatusCode() {
    }

    /** minimal constructor */
    public OperatingStatusCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public OperatingStatusCode(String code, String description, Set<ReleasePoint> releasePoints,
            Set<EmissionsUnit> emissionsUnits, Set<Facility> facilities, Set<EmissionsProcess> emissionsProcesses) {
        this.code = code;
        this.description = description;
        this.releasePoints = releasePoints;
        this.emissionsUnits = emissionsUnits;
        this.facilities = facilities;
        this.emissionsProcesses = emissionsProcesses;
    }

    // Property accessors
    @Id

    @Column(name = "code", unique = true, nullable = false, length = 20)

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "description", length = 200)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")

    public Set<ReleasePoint> getReleasePoints() {
        return this.releasePoints;
    }

    public void setReleasePoints(Set<ReleasePoint> releasePoints) {
        this.releasePoints = releasePoints;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")

    public Set<EmissionsUnit> getEmissionsUnits() {
        return this.emissionsUnits;
    }

    public void setEmissionsUnits(Set<EmissionsUnit> emissionsUnits) {
        this.emissionsUnits = emissionsUnits;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")

    public Set<Facility> getFacilities() {
        return this.facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")

    public Set<EmissionsProcess> getEmissionsProcesses() {
        return this.emissionsProcesses;
    }

    public void setEmissionsProcesses(Set<EmissionsProcess> emissionsProcesses) {
        this.emissionsProcesses = emissionsProcesses;
    }

}