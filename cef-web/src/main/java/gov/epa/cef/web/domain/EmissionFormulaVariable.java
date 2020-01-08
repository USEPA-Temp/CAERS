package gov.epa.cef.web.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "emission_formula_variable")
public class EmissionFormulaVariable extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emission_id", nullable = false)
    private Emission emission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emission_factor_variable_code", nullable = false)
    private EmissionFactorVariableCode emissionFactorVariableCode;

    @Column(name = "value")
    private BigDecimal value;

    /***
     * Default constructor
     */
    public EmissionFormulaVariable() {}

    /***
     * Copy constructor 
     * @param emission The emission that the copied variable object should be associated with
     * @param originalVariable The variable object that is being copied
     */
    public EmissionFormulaVariable(Emission emission, EmissionFormulaVariable originalVariable) {
        this.id = originalVariable.getId();
        this.emission = emission;
        this.emissionFactorVariableCode = originalVariable.getEmissionFactorVariableCode();
        this.value = originalVariable.getValue();
    }

    public Emission getEmission() {
        return emission;
    }

    public void setEmission(Emission emission) {
        this.emission = emission;
    }

    public EmissionFactorVariableCode getEmissionFactorVariableCode() {
        return emissionFactorVariableCode;
    }

    public void setEmissionFactorVariableCode(EmissionFactorVariableCode emissionFactorVariableCode) {
        this.emissionFactorVariableCode = emissionFactorVariableCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
        this.id = null;
    }
}
