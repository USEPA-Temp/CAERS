package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * UnitTypeCode entity
 */
@Entity
@Table(name = "unit_type_code")
public class UnitTypeCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}