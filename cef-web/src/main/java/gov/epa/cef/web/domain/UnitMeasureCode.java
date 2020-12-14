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
    
    @Column(name = "fuel_ef_denominator", nullable = false)
    private Boolean fuelEfDenominator;
    
    @Column(name = "heat_content_unit", nullable = false)
    private Boolean heatContentUnit;

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

    public Boolean getFuelEfDenominator() {
		return fuelEfDenominator;
	}

	public void setFuelEfDenominator(Boolean fuelEfDenominator) {
		this.fuelEfDenominator = fuelEfDenominator;
	}

	public Boolean getHeatContentUnit() {
		return heatContentUnit;
	}

	public void setHeatContentUnit(Boolean heatContentUnit) {
		this.heatContentUnit = heatContentUnit;
	}

	public Boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

}
