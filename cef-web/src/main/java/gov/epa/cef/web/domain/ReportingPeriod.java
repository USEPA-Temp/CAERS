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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_period_type_code", nullable = false)
    private ReportingPeriodCode reportingPeriodTypeCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_operating_type_code", nullable = false)
    private OperatingStatusCode emissionsOperatingTypeCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_parameter_type_code", nullable = false)
    private CalculationParameterTypeCode calculationParameterTypeCode;
    
    @Column(name = "calculation_parameter_value", nullable = false, precision = 131089, scale = 0)
    private BigDecimal calculationParameterValue;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_parameter_uom", nullable = false)
    private UnitMeasureCode calculationParameterUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_material_code", nullable = false)
    private CalculationMaterialCode calculationMaterialCode;
    
    @Column(name = "comments", length = 200)
    private String comments;
    
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

    public ReportingPeriodCode getReportingPeriodTypeCode() {
        return this.reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(ReportingPeriodCode reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public OperatingStatusCode getEmissionsOperatingTypeCode() {
        return this.emissionsOperatingTypeCode;
    }

    public void setEmissionsOperatingTypeCode(OperatingStatusCode emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    public CalculationParameterTypeCode getCalculationParameterTypeCode() {
        return this.calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(CalculationParameterTypeCode calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public BigDecimal getCalculationParameterValue() {
        return this.calculationParameterValue;
    }

    public void setCalculationParameterValue(BigDecimal calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    public UnitMeasureCode getCalculationParameterUom() {
        return this.calculationParameterUom;
    }

    public void setCalculationParameterUom(UnitMeasureCode calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    public CalculationMaterialCode getCalculationMaterialCode() {
        return this.calculationMaterialCode;
    }

    public void setCalculationMaterialCode(CalculationMaterialCode calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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