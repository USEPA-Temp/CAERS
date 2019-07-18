package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ReportSummaryDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String casId;
    private String pollutantName;
    private String pollutantType;
    private Double fugitiveTotal;
    private Double stackTotal;
    private String uom;
    private Double emissionsTonsTotal;
    private Double previousYearTotal;
    private Short reportYear;
    private Long facilitySiteId;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    
    public String getUom() {
        return this.uom;
    }
    public void setUom(String uom) {
        this.uom = uom;
    }
    
    public Double getEmissionsTonsTotal() {
        return this.emissionsTonsTotal;
    }
    public void setEmissionsTonsTotal(Double emissionsTonsTotal) {
        this.emissionsTonsTotal = emissionsTonsTotal;
    }
    
    public Double getPreviousYearTotal() {
        return this.previousYearTotal;
    }
    public void setPreviousYearTotal(Double previousYearTotal) {
        this.previousYearTotal = previousYearTotal;
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


}