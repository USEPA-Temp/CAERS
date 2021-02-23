package gov.epa.cef.web.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import gov.epa.cef.web.domain.common.BaseEntity;

/**
 * FuelUseSccCode entity
 */
@Entity
@Table(name = "fuel_use_scc")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FuelUseSccCode extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scc_code", nullable = false)
	private PointSourceSccCode sccCode;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_material_code", nullable = false)
	private CalculationMaterialCode calculationMaterialCode;
	
	@Column(name = "fuel_use_types")
    private String fuelUseTypes;

	// Property accessors
	
	public PointSourceSccCode getSccCode() {
		return sccCode;
	}

	public void setSccCode(PointSourceSccCode sccCode) {
		this.sccCode = sccCode;
	}

	public CalculationMaterialCode getCalculationMaterialCode() {
		return calculationMaterialCode;
	}

	public void setCalculationMaterialCode(CalculationMaterialCode calculationMaterialCode) {
		this.calculationMaterialCode = calculationMaterialCode;
	}

	public String getFuelUseTypes() {
		return fuelUseTypes;
	}

	public void setFuelUseTypes(String fuelUseTypes) {
		this.fuelUseTypes = fuelUseTypes;
	}

}
