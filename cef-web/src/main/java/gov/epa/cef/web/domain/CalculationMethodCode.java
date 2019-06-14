package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

@Entity
@Table(name = "calculation_method_code")
public class CalculationMethodCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}
