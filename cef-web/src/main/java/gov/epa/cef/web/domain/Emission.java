package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Emission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emission", schema = "public")

public class Emission implements java.io.Serializable {

    // Fields

    private Long id;
    private ReportingPeriod reportingPeriod;
    private String pollutantCode;
    private String pollutantName;
    private Integer totalEmissions;
    private String emissionsUomCode;
    private BigDecimal emissionsFactor;
    private String emissionsFactorText;
    private String emissionsCalcMethodCode;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;

    // Constructors

    /** default constructor */
    public Emission() {
    }

    /** full constructor */
    public Emission(Long id, ReportingPeriod reportingPeriod, String pollutantCode, String pollutantName,
            Integer totalEmissions, String emissionsUomCode, BigDecimal emissionsFactor, String emissionsFactorText,
            String emissionsCalcMethodCode, String createdBy, Timestamp createdDate, String lastModifiedBy,
            Timestamp lastModifiedDate) {
        this.id = id;
        this.reportingPeriod = reportingPeriod;
        this.pollutantCode = pollutantCode;
        this.pollutantName = pollutantName;
        this.totalEmissions = totalEmissions;
        this.emissionsUomCode = emissionsUomCode;
        this.emissionsFactor = emissionsFactor;
        this.emissionsFactorText = emissionsFactorText;
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
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
    @JoinColumn(name = "reporting_period_id", nullable = false)

    public ReportingPeriod getReportingPeriod() {
        return this.reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    @Column(name = "pollutant_code", nullable = false, length = 20)

    public String getPollutantCode() {
        return this.pollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    @Column(name = "pollutant_name", nullable = false, length = 100)

    public String getPollutantName() {
        return this.pollutantName;
    }

    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }

    @Column(name = "total_emissions", nullable = false, precision = 6, scale = 0)

    public Integer getTotalEmissions() {
        return this.totalEmissions;
    }

    public void setTotalEmissions(Integer totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    @Column(name = "emissions_uom_code", nullable = false, length = 20)

    public String getEmissionsUomCode() {
        return this.emissionsUomCode;
    }

    public void setEmissionsUomCode(String emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    @Column(name = "emissions_factor", nullable = false, precision = 131089, scale = 0)

    public BigDecimal getEmissionsFactor() {
        return this.emissionsFactor;
    }

    public void setEmissionsFactor(BigDecimal emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
    }

    @Column(name = "emissions_factor_text", nullable = false, length = 100)

    public String getEmissionsFactorText() {
        return this.emissionsFactorText;
    }

    public void setEmissionsFactorText(String emissionsFactorText) {
        this.emissionsFactorText = emissionsFactorText;
    }

    @Column(name = "emissions_calc_method_code", nullable = false, length = 20)

    public String getEmissionsCalcMethodCode() {
        return this.emissionsCalcMethodCode;
    }

    public void setEmissionsCalcMethodCode(String emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
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

}