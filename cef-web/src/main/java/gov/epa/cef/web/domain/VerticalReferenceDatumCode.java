package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * VerticalReferenceDatumCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vertical_reference_datum_code")
public class VerticalReferenceDatumCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

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

}