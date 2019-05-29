package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Agency entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "agency")

public class Agency implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @Id
    @Column(name = "id", unique = true, nullable = false, scale = 0)
    private Long id;
    
    @Column(name = "type_code", length = 20)
    private String typeCode;
    
    @Column(name = "description", length = 200)
    private String description;

    // Constructors

    /** default constructor */
    public Agency() {
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}