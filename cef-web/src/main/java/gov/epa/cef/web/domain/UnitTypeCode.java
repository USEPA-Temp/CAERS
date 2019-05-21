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
 * UnitTypeCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "unit_type_code", schema = "public")

public class UnitTypeCode implements java.io.Serializable {

    // Fields

    private String code;
    private String description;
    private Set<EmissionsUnit> emissionsUnits = new HashSet<EmissionsUnit>(0);

    // Constructors

    /** default constructor */
    public UnitTypeCode() {
    }

    /** minimal constructor */
    public UnitTypeCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public UnitTypeCode(String code, String description, Set<EmissionsUnit> emissionsUnits) {
        this.code = code;
        this.description = description;
        this.emissionsUnits = emissionsUnits;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "unitTypeCode")

    public Set<EmissionsUnit> getEmissionsUnits() {
        return this.emissionsUnits;
    }

    public void setEmissionsUnits(Set<EmissionsUnit> emissionsUnits) {
        this.emissionsUnits = emissionsUnits;
    }

}