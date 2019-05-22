package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ReportingPeriod entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reporting_period")

public class ReportingPeriod implements java.io.Serializable {

    // Fields

    private Long id;
    private EmissionsProcess emissionsProcess;
    private String reportingPeriodTypeCode;
    private String emissionsOperatingTypeCode;
    private String calculationParameterTypeCode;
    private BigDecimal calculationParameterValue;
    private String calculationParameterUom;
    private String calculationMaterialCode;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private Set<Emission> emissions = new HashSet<Emission>(0);
    private Set<OperatingDetail> operatingDetails = new HashSet<OperatingDetail>(0);

    // Constructors

    /** default constructor */
    public ReportingPeriod() {
    }

    /** minimal constructor */
    public ReportingPeriod(Long id, EmissionsProcess emissionsProcess, String reportingPeriodTypeCode,
            String emissionsOperatingTypeCode, String calculationParameterTypeCode,
            BigDecimal calculationParameterValue, String calculationParameterUom, String calculationMaterialCode,
            String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.emissionsProcess = emissionsProcess;
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
        this.calculationParameterTypeCode = calculationParameterTypeCode;
        this.calculationParameterValue = calculationParameterValue;
        this.calculationParameterUom = calculationParameterUom;
        this.calculationMaterialCode = calculationMaterialCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public ReportingPeriod(Long id, EmissionsProcess emissionsProcess, String reportingPeriodTypeCode,
            String emissionsOperatingTypeCode, String calculationParameterTypeCode,
            BigDecimal calculationParameterValue, String calculationParameterUom, String calculationMaterialCode,
            String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate,
            Set<Emission> emissions, Set<OperatingDetail> operatingDetails) {
        this.id = id;
        this.emissionsProcess = emissionsProcess;
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
        this.calculationParameterTypeCode = calculationParameterTypeCode;
        this.calculationParameterValue = calculationParameterValue;
        this.calculationParameterUom = calculationParameterUom;
        this.calculationMaterialCode = calculationMaterialCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.emissions = emissions;
        this.operatingDetails = operatingDetails;
    }

    // Property accessors
    @Id

    @Column(name = "id", unique = true, nullable = false)

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_process_id", nullable = false)

    public EmissionsProcess getEmissionsProcess() {
        return this.emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcess emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    @Column(name = "reporting_period_type_code", nullable = false, length = 20)

    public String getReportingPeriodTypeCode() {
        return this.reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(String reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    @Column(name = "emissions_operating_type_code", nullable = false, length = 20)

    public String getEmissionsOperatingTypeCode() {
        return this.emissionsOperatingTypeCode;
    }

    public void setEmissionsOperatingTypeCode(String emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    @Column(name = "calculation_parameter_type_code", nullable = false, length = 20)

    public String getCalculationParameterTypeCode() {
        return this.calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(String calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    @Column(name = "calculation_parameter_value", nullable = false, precision = 131089, scale = 0)

    public BigDecimal getCalculationParameterValue() {
        return this.calculationParameterValue;
    }

    public void setCalculationParameterValue(BigDecimal calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    @Column(name = "calculation_parameter_uom", nullable = false, length = 20)

    public String getCalculationParameterUom() {
        return this.calculationParameterUom;
    }

    public void setCalculationParameterUom(String calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    @Column(name = "calculation_material_code", nullable = false, length = 20)

    public String getCalculationMaterialCode() {
        return this.calculationMaterialCode;
    }

    public void setCalculationMaterialCode(String calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    @Column(name = "created_by", nullable = false)

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_date", nullable = false, length = 29)

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_modified_by", nullable = false)

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "last_modified_date", nullable = false, length = 29)

    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportingPeriod")

    public Set<Emission> getEmissions() {
        return this.emissions;
    }

    public void setEmissions(Set<Emission> emissions) {
        this.emissions = emissions;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reportingPeriod")

    public Set<OperatingDetail> getOperatingDetails() {
        return this.operatingDetails;
    }

    public void setOperatingDetails(Set<OperatingDetail> operatingDetails) {
        this.operatingDetails = operatingDetails;
    }

}