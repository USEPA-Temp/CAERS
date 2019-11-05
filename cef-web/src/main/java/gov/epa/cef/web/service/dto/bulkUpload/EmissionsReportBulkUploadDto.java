package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;
import java.util.List;

public class EmissionsReportBulkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String frsFacilityId;
    private String eisProgramId;
    private String agencyCode;
    private Short year;
    private String status;
    private String validationStatus;
    private List<FacilitySiteBulkUploadDto> facilitySites;
    private List<EmissionsUnitBulkUploadDto> emissionsUnits;
    private List<EmissionsProcessBulkUploadDto> emissionsProcesses;
    private List<ReleasePointBulkUploadDto> releasePoints;
    private List<ReleasePointApptBulkUploadDto> releasePointAppts;
    private List<ReportingPeriodBulkUploadDto> reportingPeriods;
    private List<OperatingDetailBulkUploadDto> operatingDetails;
    private List<EmissionBulkUploadDto> emissions;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFrsFacilityId() {
        return frsFacilityId;
    }
    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    public String getEisProgramId() {
        return eisProgramId;
    }
    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }
    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Short getYear() {
        return year;
    }
    public void setYear(Short year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getValidationStatus() {
        return validationStatus;
    }
    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    
    public void setFacilitySites(List<FacilitySiteBulkUploadDto> facilitySites) {
        this.facilitySites = facilitySites;
    }
    public List<FacilitySiteBulkUploadDto> getFacilitySites() {
        return facilitySites;
    }

    public void setEmissionsUnits(List<EmissionsUnitBulkUploadDto> emissionsUnits) {
        this.emissionsUnits = emissionsUnits;
    }
    public List<EmissionsUnitBulkUploadDto> getEmissionsUnits() {
        return emissionsUnits;
    }
    
    public void setEmissionsProcesses(List<EmissionsProcessBulkUploadDto> emissionsProcesses) {
        this.emissionsProcesses = emissionsProcesses;
    }
    public List<EmissionsProcessBulkUploadDto> getEmissionsProcesses() {
        return emissionsProcesses;
    }
    
    public void setReleasePoints(List<ReleasePointBulkUploadDto> releasePoints) {
        this.releasePoints = releasePoints;
    }
    public List<ReleasePointBulkUploadDto> getReleasePoints() {
        return releasePoints;
    }
    
    public void setReleasePointAppts(List<ReleasePointApptBulkUploadDto> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
    }
    public List<ReleasePointApptBulkUploadDto> getReleasePointAppts() {
        return releasePointAppts;
    }
    
    public void setReportingPeriods(List<ReportingPeriodBulkUploadDto> reportingPeriods) {
        this.reportingPeriods = reportingPeriods;
    }
    public List<ReportingPeriodBulkUploadDto> getReportingPeriods() {
        return reportingPeriods;
    }
    public List<OperatingDetailBulkUploadDto> getOperatingDetails() {
        return operatingDetails;
    }
    public void setOperatingDetails(List<OperatingDetailBulkUploadDto> operatingDetails) {
        this.operatingDetails = operatingDetails;
    }
    public void setEmissions(List<EmissionBulkUploadDto> emissions) {
        this.emissions = emissions;
    }
    public List<EmissionBulkUploadDto> getEmissions() {
        return emissions;
    }
}
