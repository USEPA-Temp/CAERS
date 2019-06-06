package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * RegulatoryCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "regulatory_code")
public class RegulatoryCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Fields

    @Column(name = "code_type", length = 12)
    private String codeType;

    @Column(name = "subpart_description", length = 10)
    private String subpartDescription;

    @Column(name = "part_description", length = 2)
    private String partDescription;

    // Constructors

    /** default constructor */
    public RegulatoryCode() {
    }

    // Property accessors

    public String getCodeType() {
        return this.codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
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