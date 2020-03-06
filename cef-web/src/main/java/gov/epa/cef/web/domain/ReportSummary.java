package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import gov.epa.cef.web.domain.common.BaseEntity;

/**
 * ReportSummary entity
 */
@Entity
@Table(name = "vw_report_summary")
public class ReportSummary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "pollutant_code")
    private String pollutantCode;
    
    @Column(name = "pollutant_cas_id")
    private String casId;
    
    @Column(name = "pollutant_name")
    private String pollutantName;
    
    @Column(name = "pollutant_type")
    private String pollutantType;
    
    @Column(name = "fugitive_total")
    private Double fugitiveTotal;
    
    @Column(name = "stack_total")
    private Double stackTotal;
    
    @Column(name = "fugitive_tons_total")
    private Double fugitiveTonsTotal;
    
    @Column(name = "stack_tons_total")
    private Double stackTonsTotal;
    
    @Column(name = "emissions_tons_total")
    private Double emissionsTonsTotal;
	
	@Column(name = "emissions_total")
    private Double emissionsTotal;
    
    @Column(name = "previous_year_total")
    private Double previousYearTotal ;
    
    @Column(name = "previous_year_tons_total")
    private Double previousYearTonsTotal ;
    
    @Column(name = "report_year")
    private Short reportYear;
    
    @Column(name = "facility_site_id")
    private Long facilitySiteId ;
    
    @Column(name = "previous_year")
    private Short previousYear;

    public String getPollutantCode() {
		return pollutantCode;
	}
	public void setPollutantCode(String pollutantCode) {
		this.pollutantCode = pollutantCode;
	}

    public String getCasId() {
        return this.casId;
    }
    public void setCasId(String casId) {
        this.casId = casId;
    }
    
    public String getPollutantName() {
        return this.pollutantName;
    }
    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }
    
    public String getPollutantType() {
        return this.pollutantType;
    }
    public void setPollutantType(String pollutantType) {
        this.pollutantType = pollutantType;
    }
    
    public Double getFugitiveTotal() {
        return this.fugitiveTotal;
    }
    public void setFugitiveTotal(Double fugitiveTotal) {
        this.fugitiveTotal = fugitiveTotal;
    }
    
    public Double getStackTotal() {
        return this.stackTotal;
    }
    public void setStackTotal(Double stackTotal) {
        this.stackTotal = stackTotal;
    }
   
	public Double getFugitiveTonsTotal() {
		return fugitiveTonsTotal;
	}
	public void setFugitiveTonsTotal(Double fugitiveTonsTotal) {
		this.fugitiveTonsTotal = fugitiveTonsTotal;
	}
	public Double getStackTonsTotal() {
		return stackTonsTotal;
	}
	public void setStackTonsTotal(Double stackTonsTotal) {
		this.stackTonsTotal = stackTonsTotal;
	}
    
    public Double getEmissionsTonsTotal() {
        return this.emissionsTonsTotal;
    }
    
    public void setEmissionsTonsTotal(Double emissionsTonsTotal) {
        this.emissionsTonsTotal = emissionsTonsTotal;
    }
    
    public Double getEmissionsTotal() {
		return emissionsTotal;
	}
    
	public void setEmissionsTotal(Double emissionsTotal) {
		this.emissionsTotal = emissionsTotal;
	}
    
    public Double getPreviousYearTotal() {
        return this.previousYearTotal;
    }
    public void setPreviousYearTotal(Double previousYearTotal) {
        this.previousYearTotal = previousYearTotal;
    }
    
    public Double getPreviousYearTonsTotal() {
		return previousYearTonsTotal;
	}
	public void setPreviousYearTonsTotal(Double previousYearTonsTotal) {
		this.previousYearTonsTotal = previousYearTonsTotal;
	}
	public Short getReportYear() {
        return this.reportYear;
    }
    public void setReportYear(Short reportYear) {
        this.reportYear = reportYear;
    }
    
    public Long getFacilitySiteId() {
        return this.facilitySiteId;
    }
    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
    }
    public Short getPreviousYear() {
        return previousYear;
    }
    public void setPreviousYear(Short previousYear) {
        this.previousYear = previousYear;
    }


}