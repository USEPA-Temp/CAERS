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
    
    private static final long serialVersionUID = 1L;

    // Fields
    @Id
    @Column(name = "code", unique = true, nullable = false, length = 20)
    private String code;
    
    @Column(name = "description", length = 200)
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "programSystemCode")
    private Set<ReleasePoint> releasePoints = new HashSet<ReleasePoint>(0);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "programSystemCode")
    private Set<Facility> facilities = new HashSet<Facility>(0);

    // Constructors

    /** default constructor */
    public ProgramSystemCode() {
    }

    // Property accessors

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

    public Set<Facility> getFacilities() {
        return this.facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

}