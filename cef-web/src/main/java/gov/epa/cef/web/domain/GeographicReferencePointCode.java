package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * GeographicReferencePointCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "geographic_reference_point_code")
public class GeographicReferencePointCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public GeographicReferencePointCode() {
    }

}