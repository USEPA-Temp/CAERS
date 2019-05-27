package gov.epa.cef.web.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * OperatingStatusCode entity
 */
@Entity
@Table(name = "operating_status_code")
public class OperatingStatusCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code", unique = true, nullable = false, length = 20)
    private String code;

    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")
    @JsonIgnore
    private Set<ReleasePoint> releasePoints = new HashSet<ReleasePoint>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")
    @JsonIgnore
    private Set<EmissionsUnit> emissionsUnits = new HashSet<EmissionsUnit>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")
    @JsonIgnore
    private Set<Facility> facilities = new HashSet<Facility>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operatingStatusCode")
    @JsonIgnore
    private Set<EmissionsProcess> emissionsProcesses = new HashSet<EmissionsProcess>(0);


    /** default constructor */
    public OperatingStatusCode() {
    }

    
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ReleasePoint> getReleasePoints() {
        return this.releasePoints;
    }
    public void setReleasePoints(Set<ReleasePoint> releasePoints) {
        this.releasePoints = releasePoints;
    }

    public Set<EmissionsUnit> getEmissionsUnits() {
        return this.emissionsUnits;
    }
    public void setEmissionsUnits(Set<EmissionsUnit> emissionsUnits) {
        this.emissionsUnits = emissionsUnits;
    }

    public Set<Facility> getFacilities() {
        return this.facilities;
    }
    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

    public Set<EmissionsProcess> getEmissionsProcesses() {
        return this.emissionsProcesses;
    }
    public void setEmissionsProcesses(Set<EmissionsProcess> emissionsProcesses) {
        this.emissionsProcesses = emissionsProcesses;
    }
}