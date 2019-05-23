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
 * ProgramSystemCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "program_system_code")

public class ProgramSystemCode implements java.io.Serializable {

    // Fields

    private String code;
    private String description;
    private Set<ReleasePoint> releasePoints = new HashSet<ReleasePoint>(0);
    private Set<Facility> facilities = new HashSet<Facility>(0);

    // Constructors

    /** default constructor */
    public ProgramSystemCode() {
    }

    /** minimal constructor */
    public ProgramSystemCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public ProgramSystemCode(String code, String description, Set<ReleasePoint> releasePoints,
            Set<Facility> facilities) {
        this.code = code;
        this.description = description;
        this.releasePoints = releasePoints;
        this.facilities = facilities;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "programSystemCode")

    public Set<ReleasePoint> getReleasePoints() {
        return this.releasePoints;
    }

    public void setReleasePoints(Set<ReleasePoint> releasePoints) {
        this.releasePoints = releasePoints;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "programSystemCode")

    public Set<Facility> getFacilities() {
        return this.facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

}