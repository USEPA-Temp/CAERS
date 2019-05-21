package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RegulatoryCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "regulatory_code", schema = "public")

public class RegulatoryCode implements java.io.Serializable {

    // Fields

    private String code;
    private String codeType;
    private String description;
    private String subpartDescription;
    private String partDescription;

    // Constructors

    /** default constructor */
    public RegulatoryCode() {
    }

    /** minimal constructor */
    public RegulatoryCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public RegulatoryCode(String code, String codeType, String description, String subpartDescription,
            String partDescription) {
        this.code = code;
        this.codeType = codeType;
        this.description = description;
        this.subpartDescription = subpartDescription;
        this.partDescription = partDescription;
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

    @Column(name = "code_type", length = 12)

    public String getCodeType() {
        return this.codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Column(name = "description", length = 200)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "subpart_description", length = 10)

    public String getSubpartDescription() {
        return this.subpartDescription;
    }

    public void setSubpartDescription(String subpartDescription) {
        this.subpartDescription = subpartDescription;
    }

    @Column(name = "part_description", length = 2)

    public String getPartDescription() {
        return this.partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

}