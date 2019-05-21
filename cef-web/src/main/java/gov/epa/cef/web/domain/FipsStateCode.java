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
 * FipsStateCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fips_state_code", schema = "public")

public class FipsStateCode implements java.io.Serializable {

    // Fields

    private String code;
    private String uspsCode;
    private String name;
    private Set<FipsCounty> fipsCounties = new HashSet<FipsCounty>(0);

    // Constructors

    /** default constructor */
    public FipsStateCode() {
    }

    /** minimal constructor */
    public FipsStateCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public FipsStateCode(String code, String uspsCode, String name, Set<FipsCounty> fipsCounties) {
        this.code = code;
        this.uspsCode = uspsCode;
        this.name = name;
        this.fipsCounties = fipsCounties;
    }

    // Property accessors
    @Id

    @Column(name = "code", unique = true, nullable = false, length = 2)

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "usps_code", length = 2)

    public String getUspsCode() {
        return this.uspsCode;
    }

    public void setUspsCode(String uspsCode) {
        this.uspsCode = uspsCode;
    }

    @Column(name = "name", length = 200)

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fipsStateCode")

    public Set<FipsCounty> getFipsCounties() {
        return this.fipsCounties;
    }

    public void setFipsCounties(Set<FipsCounty> fipsCounties) {
        this.fipsCounties = fipsCounties;
    }

}