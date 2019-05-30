package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
 * ReportingPeriod entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reporting_period")

public class ReportingPeriod extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_process_id", nullable = false)
    private EmissionsProcess emissionsProcess;
    
    @Column(name = "reporting_period_type_code", nullable = false, length = 20)
    private String reportingPeriodTypeCode;
    
    @Column(name = "emissions_operating_type_code", nullable = false, length = 20)
    private String emissionsOperatingTypeCode;
    
    @Column(name = "calculation_parameter_type_code", nullable = false, length = 20)
    private String calculationParameterTypeCode;
    
    @Column(name = "calculation_parameter_value", nullable = false, precision = 131089, scale = 0)
    private BigDecimal calculationParameterValue;
    
    @Column(name = "calculation_parameter_uom", nullable = false, length = 20)
    private String calculationParameterUom;
    
    @Column(name = "calculation_material_code", nullable = false, length = 20)
    private String calculationMaterialCode;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportingPeriod")
    private Set<Emission> emissions = new HashSet<Emission>(0);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportingPeriod")
    private Set<OperatingDetail> operatingDetails = new HashSet<OperatingDetail>(0);

    // Constructors

    /** default constructor */
    public ReportingPeriod() {
    }

    public EmissionsProcess getEmissionsProcess() {
        return this.emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcess emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    public String getReportingPeriodTypeCode() {
        return this.reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(String reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public String getEmissionsOperatingTypeCode() {
        return this.emissionsOperatingTypeCode;
    }

    public void setEmissionsOperatingTypeCode(String emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    public String getCalculationParameterTypeCode() {
        return this.calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(String calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public BigDecimal getCalculationParameterValue() {
        return this.calculationParameterValue;
    }

    public void setCalculationParameterValue(BigDecimal calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    public String getCalculationParameterUom() {
        return this.calculationParameterUom;
    }

    public void setCalculationParameterUom(String calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    public String getCalculationMaterialCode() {
        return this.calculationMaterialCode;
    }

    public void setCalculationMaterialCode(String calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public Set<Emission> getEmissions() {
        return this.emissions;
    }

    public void setEmissions(Set<Emission> emissions) {
        this.emissions = emissions;
    }

    public Set<OperatingDetail> getOperatingDetails() {
        return this.operatingDetails;
    }

    public void setOperatingDetails(Set<OperatingDetail> operatingDetails) {
        this.operatingDetails = operatingDetails;
    }

}