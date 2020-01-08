package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * Emission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emission")
public class Emission extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_period_id", nullable = false)
    private ReportingPeriod reportingPeriod;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pollutant_code", nullable = false)
    private Pollutant pollutant;
    
    @Column(name = "total_emissions", nullable = false)
    private BigDecimal totalEmissions;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_uom_code", nullable = false)
    private UnitMeasureCode emissionsUomCode;
    
    @Column(name = "formula_indicator")
    private Boolean formulaIndicator;
    
    @Column(name = "emissions_factor")
    private BigDecimal emissionsFactor;
    
    @Column(name = "emissions_factor_formula", length = 100)
    private String emissionsFactorFormula;
    
    @Column(name = "emissions_factor_text", length = 100)
    private String emissionsFactorText;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_calc_method_code", nullable = false)
    private CalculationMethodCode emissionsCalcMethodCode;

    @Column(name = "comments", length = 400)
    private String comments;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_numerator_uom")
    private UnitMeasureCode emissionsNumeratorUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_denominator_uom")
    private UnitMeasureCode emissionsDenominatorUom;
    
    @Column(name = "calculated_emissions_tons")
    private BigDecimal calculatedEmissionsTons;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "emission")
    private List<EmissionFormulaVariable> variables = new ArrayList<>();

    
    /***
     * Default constructor
     */
    public Emission() {}
    
    
    /***
     * Copy constructor 
     * @param reportingPeriod The reporting period that the copied Emission object should be associated with
     * @param originalEmission The emission object that is being copied
     */
    public Emission(ReportingPeriod reportingPeriod, Emission originalEmission) {
		this.id = originalEmission.getId();
        this.reportingPeriod = reportingPeriod;
        this.pollutant = originalEmission.getPollutant();
        this.totalEmissions = originalEmission.getTotalEmissions();
        this.emissionsUomCode = originalEmission.getEmissionsUomCode();
        this.emissionsFactor = originalEmission.getEmissionsFactor();
        this.emissionsFactorText = originalEmission.getEmissionsFactorText();
        this.emissionsCalcMethodCode = originalEmission.getEmissionsCalcMethodCode();
        this.comments = originalEmission.getComments();
        this.emissionsNumeratorUom = originalEmission.getEmissionsNumeratorUom();
        this.emissionsDenominatorUom = originalEmission.getEmissionsDenominatorUom();
        this.calculatedEmissionsTons = originalEmission.getCalculatedEmissionsTons();
        this.formulaIndicator = originalEmission.getFormulaIndicator();
        this.emissionsFactorFormula = originalEmission.getEmissionsFactorFormula();

        for (EmissionFormulaVariable variable : originalEmission.getVariables()) {
            this.variables.add(new EmissionFormulaVariable(this, variable));
        }
    }

    public ReportingPeriod getReportingPeriod() {
        return this.reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public Pollutant getPollutant() {
        return pollutant;
    }

    public void setPollutant(Pollutant pollutant) {
        this.pollutant = pollutant;
    }

    public BigDecimal getTotalEmissions() {
        return this.totalEmissions;
    }

    public void setTotalEmissions(BigDecimal totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public UnitMeasureCode getEmissionsUomCode() {
        return this.emissionsUomCode;
    }

    public void setEmissionsUomCode(UnitMeasureCode emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    public Boolean getFormulaIndicator() {
        return formulaIndicator;
    }

    public void setFormulaIndicator(Boolean formulaIndicator) {
        this.formulaIndicator = formulaIndicator;
    }

    public BigDecimal getEmissionsFactor() {
        return this.emissionsFactor;
    }

    public void setEmissionsFactor(BigDecimal emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
    }

    public String getEmissionsFactorFormula() {
        return emissionsFactorFormula;
    }

    public void setEmissionsFactorFormula(String emissionsFactorFormula) {
        this.emissionsFactorFormula = emissionsFactorFormula;
    }

    public String getEmissionsFactorText() {
        return this.emissionsFactorText;
    }

    public void setEmissionsFactorText(String emissionsFactorText) {
        this.emissionsFactorText = emissionsFactorText;
    }

    public CalculationMethodCode getEmissionsCalcMethodCode() {
        return this.emissionsCalcMethodCode;
    }

    public void setEmissionsCalcMethodCode(CalculationMethodCode emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getCalculatedEmissionsTons() {
        return calculatedEmissionsTons;
    }

    public void setCalculatedEmissionsTons(BigDecimal calculatedEmissionsTons) {
        this.calculatedEmissionsTons = calculatedEmissionsTons;
    }

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

    public List<EmissionFormulaVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<EmissionFormulaVariable> variables) {

        this.variables.clear();
        if (variables != null) {
            this.variables.addAll(variables);
        }
    }


    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}