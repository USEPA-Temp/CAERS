package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UnitMeasureCode entity
 */
@Entity
@Table(name = "unit_measure_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class UnitMeasureCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "unit_type", nullable = false)
    private String unitType;
    
    @Column(name = "calculation_variable")
    private String calculationVariable;

    @Column(name = "ef_numerator", nullable = false)
    private Boolean efNumerator;

    @Column(name = "ef_denominator", nullable = false)
    private Boolean efDenominator;
    
    @Column(name = "unit_design_capacity", nullable = false)
    private Boolean unitDesignCapacity;
    
    @Column(name = "fuel_use_uom", nullable = false)
    private Boolean fuelUseUom;
    
    @Column(name = "fuel_use_type", nullable = false)
    private String fuelUseType;
    
    @Column(name = "heat_content_uom", nullable = false)
    private Boolean heatContentUom;

    @Column(name = "legacy", nullable = false)
    private Boolean legacy;

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getCalculationVariable() {
        return calculationVariable;
    }

    public void setCalculationVariable(String calculationVariable) {
        this.calculationVariable = calculationVariable;
    }

    public Boolean getEfNumerator() {
        return efNumerator;
    }

    public void setEfNumerator(Boolean efNumerator) {
        this.efNumerator = efNumerator;
    }

    public Boolean getEfDenominator() {
        return efDenominator;
    }

    public void setEfDenominator(Boolean efDenominator) {
        this.efDenominator = efDenominator;
    }
    
    public Boolean getUnitDesignCapacity() {
        return unitDesignCapacity;
    }

    public void setUnitDesignCapacity(Boolean unitDesignCapacity) {
        this.unitDesignCapacity = unitDesignCapacity;
    }

	public Boolean getHeatContentUom() {
		return heatContentUom;
	}

	public void setHeatContentUom(Boolean heatContentUom) {
		this.heatContentUom = heatContentUom;
	}

	public Boolean getFuelUseUom() {
		return fuelUseUom;
	}

	public void setFuelUseUom(Boolean fuelUseUom) {
		this.fuelUseUom = fuelUseUom;
	}

	public Boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

	public String getFuelUseType() {
		return fuelUseType;
	}

	public void setFuelUseType(String fuelUseType) {
		this.fuelUseType = fuelUseType;
	}

}
