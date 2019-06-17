package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FipsStateCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fips_state_code")

public class FipsStateCode implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @Id
    @Column(name = "code", unique = true, nullable = false, length = 2)
    private String code;
    
    @Column(name = "usps_code", length = 2)
    private String uspsCode;
    
    @Column(name = "name", length = 200)
    private String name;

    // Constructors

    /** default constructor */
    public FipsStateCode() {
    }

    // Property accessors
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUspsCode() {
        return this.uspsCode;
    }

    public void setUspsCode(String uspsCode) {
        this.uspsCode = uspsCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}