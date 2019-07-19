package gov.epa.cef.web.service.dto;

import java.io.Serializable;

/**
 * @author ahmahfou
 *
 */
public class SubmissionsReviewDashboardDto implements Serializable{

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;
    
    
    private Long emissionsReportId;
    private String eisProgramId;
    private String facilityName;
    private String altFacilityId;
    private String operatingStatus;
    private String industry;
    private Short lastSubmittalYear;
    private Short year;
    
    
    public Long getEmissionsReportId() {
        return emissionsReportId;
    }
    public void setEmissionsReportId(Long emissionsReportId) {
        this.emissionsReportId = emissionsReportId;
    }
    public String getEisProgramId() {
        return eisProgramId;
    }
    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }
    public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public String getAltFacilityId() {
        return altFacilityId;
    }
    public void setAltFacilityId(String altFacilityId) {
        this.altFacilityId = altFacilityId;
    }
    public String getOperatingStatus() {
        return operatingStatus;
    }
    public void setOperatingStatus(String operatingStatus) {
        this.operatingStatus = operatingStatus;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public Short getLastSubmittalYear() {
        return lastSubmittalYear;
    }
    public void setLastSubmittalYear(Short lastSubmittalYear) {
        this.lastSubmittalYear = lastSubmittalYear;
    }
    public Short getYear() {
        return year;
    }
    public void setYear(Short year) {
        this.year = year;
    }
    
}