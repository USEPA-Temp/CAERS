package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import gov.epa.cef.web.domain.common.BaseEntity;

@Entity
@Table(name = "emission_factor")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EmissionFactor extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numerator_uom_code")
    private UnitMeasureCode emissionsNumeratorUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "denominator_uom_code")
    private UnitMeasureCode emissionsDenominatorUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_parameter_type_code", nullable = false)
    private CalculationParameterTypeCode calculationParameterTypeCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_material_code", nullable = false)
    private CalculationMaterialCode calculationMaterialCode;
    
    @Column(name = "scc_code", nullable = false, precision = 8, scale = 0)
    private Integer sccCode;
    
    @Column(name = "pollutant_code", length = 12)
    private String pollutantCode;
    
    @Column(name = "formula_indicator")
    private boolean formulaIndicator;
    
    @Column(name = "control_indicator")
    private boolean controlIndicator;
    
    @Column(name = "emission_factor", nullable = false, precision = 30, scale = 20)
    private BigDecimal emissionFactor;
    
    @Column(name = "emission_factor_formula", length = 200)
    private String emissionFactorFormula;
    
    @Column(name = "description", length = 2000)
    private String description;
    
    @Column(name = "note", length = 2000)
    private String note;
    
    @Column(name = "source", length = 200)
    private String source;
    
    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    public UnitMeasureCode getEmissionsNumeratorUom() {
        return emissionsNumeratorUom;
    }

    public void setEmissionsNumeratorUom(UnitMeasureCode emissionsNumeratorUom) {
        this.emissionsNumeratorUom = emissionsNumeratorUom;
    }

    public UnitMeasureCode getEmissionsDenominatorUom() {
        return emissionsDenominatorUom;
    }

    public void setEmissionsDenominatorUom(UnitMeasureCode emissionsDenominatorUom) {
        this.emissionsDenominatorUom = emissionsDenominatorUom;
    }

    public CalculationParameterTypeCode getCalculationParameterTypeCode() {
        return calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(CalculationParameterTypeCode calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public CalculationMaterialCode getCalculationMaterialCode() {
        return calculationMaterialCode;
    }

    public void setCalculationMaterialCode(CalculationMaterialCode calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public Integer getSccCode() {
        return sccCode;
    }

    public void setSccCode(Integer sccCode) {
        this.sccCode = sccCode;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    public boolean isFormulaIndicator() {
        return formulaIndicator;
    }

    public void setFormulaIndicator(boolean formulaIndicator) {
        this.formulaIndicator = formulaIndicator;
    }

    public boolean isControlIndicator() {
        return controlIndicator;
    }

    public void setControlIndicator(boolean controlIndicator) {
        this.controlIndicator = controlIndicator;
    }

    public BigDecimal getEmissionFactor() {
        return emissionFactor;
    }

    public void setEmissionFactor(BigDecimal emissionFactor) {
        this.emissionFactor = emissionFactor;
    }

    public String getEmissionFactorFormula() {
        return emissionFactorFormula;
    }

    public void setEmissionFactorFormula(String emissionFactorFormula) {
        this.emissionFactorFormula = emissionFactorFormula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}