package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;


/**
 * OperatingStatusCode entity
 */
@Entity
@Table(name = "operating_status_code")
public class OperatingStatusCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    /** default constructor */
    public OperatingStatusCode() {
    }


}