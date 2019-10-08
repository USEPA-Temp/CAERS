package gov.epa.cef.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Table(name = "vw_submissions_review_dashboard")
public class SubmissionsReviewDashboardView implements Serializable{

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "emissions_report_id", unique = true, nullable = false)
    private Long emissionsReportId;
    
    @Column(name = "eis_program_id", nullable = false, length = 22)
    private String eisProgramId;
    
    @Column(name = "facility_name", nullable = false, length = 80)
    private String facilityName;

    @Column(name = "facility_site_id")
    private Long facilitySiteId;
    
    @Column(name = "alt_facility_id", length = 30)
    private String altFacilityId;
    
    @Column(name = "operating_status", length = 200)
    private String operatingStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "report_status")
    private ReportStatus reportStatus;
    
    @Column(name = "industry", length = 200)
    private String industry;
    
    @Column(name = "last_submittal_year")
    private Short lastSubmittalYear;
    
    @Column(name = "year", nullable = false)
    private Short year;
    
    @Column(name = "agency_code", nullable = false, length = 3)
    private String agencyCode;    
    
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
    public ReportStatus getReportStatus() {
        return reportStatus;
    }
    public void setReportStatus(ReportStatus reportStatus) {
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
    public String getAgencyCode() {
        return agencyCode;
    }
    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }
    
}
