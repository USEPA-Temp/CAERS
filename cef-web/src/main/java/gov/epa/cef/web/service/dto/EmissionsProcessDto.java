package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

public class EmissionsProcessDto implements Serializable {

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long emissionsUnitId;
    private String aircraftEngineTypeCodeCode;
    private CodeLookupDto operatingStatusCode;
    private String emissionsProcessIdentifier;
    private Short statusYear;
    private String sccCode;
    private String sccShortName;
    private String description;
    private String comments;
    private List<ReleasePointApptDto> releasePointAppts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmissionsUnitId() {
        return emissionsUnitId;
    }

    public void setEmissionsUnitId(Long emissionsUnitId) {
        this.emissionsUnitId = emissionsUnitId;
    }

    public String getAircraftEngineTypeCodeCode() {
        return aircraftEngineTypeCodeCode;
    }

    public void setAircraftEngineTypeCodeCode(String aircraftEngineTypeCodeCode) {
        this.aircraftEngineTypeCodeCode = aircraftEngineTypeCodeCode;
    }

    public CodeLookupDto getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(CodeLookupDto operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getEmissionsProcessIdentifier() {
        return emissionsProcessIdentifier;
    }

    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    public Short getStatusYear() {
        return statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public String getSccCode() {
        return sccCode;
    }

    public void setSccCode(String sccCode) {
        this.sccCode = sccCode;
    }

    public String getSccShortName() {
        return sccShortName;
    }

    public void setSccShortName(String sccShortName) {
        this.sccShortName = sccShortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<ReleasePointApptDto> getReleasePointAppts() {
        return releasePointAppts;
    }

    public void setReleasePointAppts(List<ReleasePointApptDto> releasePoints) {
        this.releasePointAppts = releasePoints;
    }
}

