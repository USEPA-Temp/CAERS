package gov.epa.cef.web.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @Column(name = "pollutant_code", nullable = false, length = 20)
    private String pollutantCode;
    
    @Column(name = "pollutant_name", nullable = false, length = 100)
    private String pollutantName;
    
    @Column(name = "total_emissions", nullable = false, precision = 6, scale = 0)
    private Integer totalEmissions;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_uom_code", nullable = false)
    private UnitMeasureCode emissionsUomCode;
    
    @Column(name = "emissions_factor", nullable = false, precision = 131089, scale = 0)
    private BigDecimal emissionsFactor;
    
    @Column(name = "emissions_factor_text", nullable = false, length = 100)
    private String emissionsFactorText;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_calc_method_code", nullable = false)
    CalculationMethodCode emissionsCalcMethodCode;
    
    @Column(name = "pollutant_cas_id", length = 100)
    private String pollutantCasId;
    
    @Column(name = "comments", length = 200)
    private String comments;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_numerator_uom")
    private UnitMeasureCode emissionsNumeratorUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_denominator_uom")
    private UnitMeasureCode emissionsDenominatorUom;
    
    @Column(name = "calculated_emissions_tons", precision = 131089, scale = 0)
    private BigDecimal calculatedEmissionsTons;

    public ReportingPeriod getReportingPeriod() {
        return this.reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public String getPollutantCode() {
        return this.pollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    public String getPollutantName() {
        return this.pollutantName;
    }

    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }

    public Integer getTotalEmissions() {
        return this.totalEmissions;
    }

    public void setTotalEmissions(Integer totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public UnitMeasureCode getEmissionsUomCode() {
        return this.emissionsUomCode;
    }

    public void setEmissionsUomCode(UnitMeasureCode emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    public BigDecimal getEmissionsFactor() {
        return this.emissionsFactor;
    }

    public void setEmissionsFactor(BigDecimal emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
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

    public String getPollutantCasId() {
        return pollutantCasId;
    }

    public void setPollutantCasId(String pollutantCasId) {
        this.pollutantCasId = pollutantCasId;
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

}