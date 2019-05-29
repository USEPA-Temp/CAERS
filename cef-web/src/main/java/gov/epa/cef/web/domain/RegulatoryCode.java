package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RegulatoryCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "regulatory_code")

public class RegulatoryCode implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @Id
    @Column(name = "code", unique = true, nullable = false, length = 20)
    private String code;
    
    @Column(name = "code_type", length = 12)
    private String codeType;
    
    @Column(name = "description", length = 200)
    private String description;
    
    @Column(name = "subpart_description", length = 10)
    private String subpartDescription;
    
    @Column(name = "part_description", length = 2)
    private String partDescription;

    // Constructors

    /** default constructor */
    public RegulatoryCode() {
    }

    // Property accessors

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeType() {
        return this.codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubpartDescription() {
        return this.subpartDescription;
    }

    public void setSubpartDescription(String subpartDescription) {
        this.subpartDescription = subpartDescription;
    }

    public String getPartDescription() {
        return this.partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

}