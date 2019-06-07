package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * UnitMeasureCode entity
 */
@Entity
@Table(name = "unit_measure_code")
public class UnitMeasureCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}