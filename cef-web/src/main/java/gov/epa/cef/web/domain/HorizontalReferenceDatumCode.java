package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * HorizontalReferenceDatumCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "horizontal_reference_datum_code")
public class HorizontalReferenceDatumCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public HorizontalReferenceDatumCode() {
    }

}