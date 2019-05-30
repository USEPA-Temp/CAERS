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
    
    @Column(name = "emissions_uom_code", nullable = false, length = 20)
    private String emissionsUomCode;
    
    @Column(name = "emissions_factor", nullable = false, precision = 131089, scale = 0)
    private BigDecimal emissionsFactor;
    
    @Column(name = "emissions_factor_text", nullable = false, length = 100)
    private String emissionsFactorText;
    
    @Column(name = "emissions_calc_method_code", nullable = false, length = 20)
    private String emissionsCalcMethodCode;

    // Constructors

    /** default constructor */
    public Emission() {
    }

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

    public String getEmissionsUomCode() {
        return this.emissionsUomCode;
    }

    public void setEmissionsUomCode(String emissionsUomCode) {
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

    public String getEmissionsCalcMethodCode() {
        return this.emissionsCalcMethodCode;
    }

    public void setEmissionsCalcMethodCode(String emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
    }
}