package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ReportSummaryDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String casId;
    private String pollutantCode;
    private String pollutantName;
    private String pollutantType;
    private Double fugitiveTotal;
    private Double stackTotal;
    private Double fugitiveTonsTotal;
    private Double stackTonsTotal;
    private Double emissionsTonsTotal;
    private Double emissionsTotal;
    private Double previousYearTotal;
    private Double previousYearTonsTotal;
    private Short reportYear;
    private Long facilitySiteId;
    private Short previousYear;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
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