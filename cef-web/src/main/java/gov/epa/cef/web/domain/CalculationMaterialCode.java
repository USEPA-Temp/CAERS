package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "calculation_material_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CalculationMaterialCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "fuel_use_material", nullable = false)
    private Boolean fuelUseMaterial;

	public Boolean getFuelUseMaterial() {
		return fuelUseMaterial;
	}

	public void setFuelUseMaterial(Boolean fuelUseMaterial) {
		this.fuelUseMaterial = fuelUseMaterial;
	}
    
}
