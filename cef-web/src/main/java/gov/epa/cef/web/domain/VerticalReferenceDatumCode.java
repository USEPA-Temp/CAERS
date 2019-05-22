package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VerticalReferenceDatumCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vertical_reference_datum_code")

public class VerticalReferenceDatumCode implements java.io.Serializable {

    // Fields

    private String code;
    private String description;

    // Constructors

    /** default constructor */
    public VerticalReferenceDatumCode() {
    }

    /** minimal constructor */
    public VerticalReferenceDatumCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public VerticalReferenceDatumCode(String code, String description) {
        this.code = code;
        this.description = description;
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

}