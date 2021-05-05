package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @Column(name = "default_heat_content_ratio", nullable = false, precision = 12, scale = 6)
    private BigDecimal defaultHeatContentRatio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heat_content_ratio_numerator")
    private UnitMeasureCode heatContentRatioNumeratorUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heat_content_ratio_denominator")
    private UnitMeasureCode heatContentRatioDenominatorUom;

	public Boolean getFuelUseMaterial() {
		return fuelUseMaterial;
	}

	public void setFuelUseMaterial(Boolean fuelUseMaterial) {
		this.fuelUseMaterial = fuelUseMaterial;
	}

	public BigDecimal getDefaultHeatContentRatio() {
		return defaultHeatContentRatio;
	}

	public void setDefaultHeatContentRatio(BigDecimal defaultHeatContentRatio) {
		this.defaultHeatContentRatio = defaultHeatContentRatio;
	}

	public UnitMeasureCode getHeatContentRatioNumeratorUom() {
		return heatContentRatioNumeratorUom;
	}

	public void setHeatContentRatioNumeratorUom(
			UnitMeasureCode heatContentRatioNumeratorUom) {
		this.heatContentRatioNumeratorUom = heatContentRatioNumeratorUom;
	}

	public UnitMeasureCode getHeatContentRatioDenominatorUom() {
		return heatContentRatioDenominatorUom;
	}

	public void setHeatContentRatioDenominatorUom(
			UnitMeasureCode heatContentRatioDenominatorUom) {
		this.heatContentRatioDenominatorUom = heatContentRatioDenominatorUom;
	}

}
