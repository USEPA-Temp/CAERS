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
 * NaicsCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "naics_code", schema = "public")

public class NaicsCode implements java.io.Serializable {

    // Fields

    private Integer code;
    private String description;
    private Set<Facility> facilities = new HashSet<Facility>(0);

    // Constructors

    /** default constructor */
    public NaicsCode() {
    }

    /** minimal constructor */
    public NaicsCode(Integer code) {
        this.code = code;
    }

    /** full constructor */
    public NaicsCode(Integer code, String description, Set<Facility> facilities) {
        this.code = code;
        this.description = description;
        this.facilities = facilities;
    }

    // Property accessors
    @Id

    @Column(name = "code", unique = true, nullable = false, precision = 6, scale = 0)

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Column(name = "description", length = 200)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "naicsCode")

    public Set<Facility> getFacilities() {
        return this.facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

}