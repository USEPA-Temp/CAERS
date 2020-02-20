package gov.epa.cef.web.domain;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseEntity;

@Entity
@Table(name = "vw_report_download")
public class ReportDownloadView extends BaseEntity{

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "report_id", nullable = false)
    private Long reportId;
    
    @Column(name = "facility_site_id")
    private Long facilitySiteId;
    
    @Column(name = "inventory_year")
    private Short reportYear;
    
    @Column(name = "emissions_unit_id", length = 20)
    private String emissionsUnitId;
    
    @Column(name = "emission_unit_description", length = 100)
    private String emissionUnitDescription;
    
    @Column(name = "process_id", length = 20)
    private String processId;
    
    @Column(name = "process_description", length = 200)
    private String processDescription;
        
    @Column(name = "pollutant_name", length = 200)
    private String pollutantName;  
    
    @Column(name = "emissions_uom_code", length = 20)
    private String emissionsUomCode;
    
    @Column(name = "emission_calc_method", length = 200)
    private String emissionsCalcMethod;
    
	@Column(name = "emissions_numerator_uom", length = 20)
    private String emissionsNumeratorUom;
    
    @Column(name = "emissions_denominator_uom", length = 20)
    private String emissionsDenominatorUom;
    
    @Column(name = "emissions_factor")
    private BigDecimal emissionsFactor;
    
    @Column(name = "emissions_factor_text", length = 100)
    private String emissionsFactorText;
    
    @Column(name = "emissions_comment", length = 400)
    private String emissionsComment;
    
    @Column(name = "reporting_period_type", length = 50)
    private String reportingPeriodType;

	@Column(name = "total_emissions")
    private BigDecimal totalEmissions;
	
    @Column(name = "last_modified_by", length = 255)
    private String lastModifiedBy;
    
	@Column(name = "last_modified_date")
	private Date lastModifiedDate;
	
    public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getEmissionsNumeratorUom() {
		return emissionsNumeratorUom;
	}

	public void setEmissionsNumeratorUom(String emissionsNumeratorUom) {
		this.emissionsNumeratorUom = emissionsNumeratorUom;
	}

	public String getEmissionsDenominatorUom() {
		return emissionsDenominatorUom;
	}

	public void setEmissionsDenominatorUom(String emissionsDenominatorUom) {
		this.emissionsDenominatorUom = emissionsDenominatorUom;
	}

	public BigDecimal getEmissionsFactor() {
		return emissionsFactor;
	}

	public void setEmissionsFactor(BigDecimal emissionsFactor) {
		this.emissionsFactor = emissionsFactor;
	}

	public String getEmissionsFactorText() {
		return emissionsFactorText;
	}

	public void setEmissionsFactorText(String emissionsFactorText) {
		this.emissionsFactorText = emissionsFactorText;
	}

	public String getEmissionsComment() {
		return emissionsComment;
	}

	public void setEmissionsComment(String emissionsComment) {
		this.emissionsComment = emissionsComment;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}

	public Short getReportYear() {
		return reportYear;
	}

	public void setReportYear(Short reportYear) {
		this.reportYear = reportYear;
	}

	public String getEmissionsUnitId() {
		return emissionsUnitId;
	}

	public void setEmissionsUnitId(String emissionsUnitId) {
		this.emissionsUnitId = emissionsUnitId;
	}

	public String getEmissionUnitDescription() {
		return emissionUnitDescription;
	}

	public void setEmissionUnitDescription(String emissionUnitDescription) {
		this.emissionUnitDescription = emissionUnitDescription;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public String getPollutantName() {
		return pollutantName;
	}

	public void setPollutantName(String pollutantName) {
		this.pollutantName = pollutantName;
	}

	public BigDecimal getTotalEmissions() {
		return totalEmissions;
	}

	public void setTotalEmissions(BigDecimal totalEmissions) {
		this.totalEmissions = totalEmissions;
	}
	public String getEmissionsCalcMethod() {
		return emissionsCalcMethod;
	}

	public void setEmissionsCalcMethod(String emissionsCalcMethod) {
		this.emissionsCalcMethod = emissionsCalcMethod;
	}

	public String getEmissionsUomCode() {
		return emissionsUomCode;
	}

	public void setEmissionsUomCode(String emissionsUomCode) {
		this.emissionsUomCode = emissionsUomCode;
	}
	     
	public String getReportingPeriodType() {
		return reportingPeriodType;
	}

	public void setReportingPeriodType(String reportingPeriodType) {
		this.reportingPeriodType = reportingPeriodType;
	}
}