package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Agency entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "agency", schema = "public")

public class Agency implements java.io.Serializable {

    // Fields

    private BigDecimal id;
    private String typeCode;
    private String description;

    // Constructors

    /** default constructor */
    public Agency() {
    }

    /** minimal constructor */
    public Agency(BigDecimal id) {
        this.id = id;
    }

    /** full constructor */
    public Agency(BigDecimal id, String typeCode, String description) {
        this.id = id;
        this.typeCode = typeCode;
        this.description = description;
    }

    // Property accessors
    @Id

    @Column(name = "id", unique = true, nullable = false, scale = 0)

    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @Column(name = "type_code", length = 20)

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "description", length = 200)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}