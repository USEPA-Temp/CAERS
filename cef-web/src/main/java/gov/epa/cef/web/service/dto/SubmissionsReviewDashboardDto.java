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
    private Long masterFacilityId;
    private String eisProgramId;
    private String facilityName;
    private Long facilitySiteId;
    private String altFacilityId;
    private String operatingStatus;
    private String reportStatus;
    private String industry;
    private Short lastSubmittalYear;
    private Short year;
    
    
    public Long getEmissionsReportId() {
        return emissionsReportId;
    }
    public void setEmissionsReportId(Long emissionsReportId) {
        this.emissionsReportId = emissionsReportId;
    }
    public Long getMasterFacilityId() {
        return masterFacilityId;
    }
    public void setMasterFacilityId(Long masterFacilityId) {
        this.masterFacilityId = masterFacilityId;
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
    public Long getFacilitySiteId() {
        return facilitySiteId;
    }
    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
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
    public String getReportStatus() {
        return reportStatus;
    }
    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
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
