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
    
    @Column(name = "uom")
    private String uom;
    
    @Column(name = "emissions_tons_total")
    private Double emissionsTonsTotal;
    
    @Column(name = "previous_year_total")
    private Double previousYearTotal ;
    
    @Column(name = "report_year")
    private Short reportYear;
    
    @Column(name = "facility_site_id")
    private Long facilitySiteId ;
    
    @Column(name = "previous_year")
    private Short previousYear;



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
    public Short getPreviousYear() {
        return previousYear;
    }
    public void setPreviousYear(Short previousYear) {
        this.previousYear = previousYear;
    }


}