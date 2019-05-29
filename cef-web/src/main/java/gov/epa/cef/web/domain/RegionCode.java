package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RegionCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "region_code")

public class RegionCode implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @Id
    @Column(name = "code", unique = true, nullable = false, length = 10)
    private String code;
    
    @Column(name = "description", length = 100)
    private String description;

    // Constructors

    /** default constructor */
    public RegionCode() {
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

}