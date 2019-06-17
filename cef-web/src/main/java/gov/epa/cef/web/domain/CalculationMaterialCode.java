package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

@Entity
@Table(name = "calculation_material_code")
public class CalculationMaterialCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}