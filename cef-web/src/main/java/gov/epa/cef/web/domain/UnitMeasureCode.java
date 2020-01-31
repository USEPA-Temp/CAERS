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

}
