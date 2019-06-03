package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

public class EmissionsProcessDto implements Serializable {

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long emissionsUnitId;
    
    private String operatingStatusCodeDescription;

    private String emissionsProcessIdentifier;

    private Short statusYear;

    private String sccCode;

    private String sccShortName;

    private String description;

    private List<ReleasePointApptDto> releasePoints;
    

    public Long getEmissionsUnitId() {
        return emissionsUnitId;
    }

    public void setEmissionsUnitId(Long emissionsUnitId) {
        this.emissionsUnitId = emissionsUnitId;
    }

    public String getOperatingStatusCodeDescription() {
        return operatingStatusCodeDescription;
    }

    public void setOperatingStatusCodeDescription(String operatingStatusCodeDescription) {
        this.operatingStatusCodeDescription = operatingStatusCodeDescription;
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

    public List<ReleasePointApptDto> getReleasePoints() {
        return releasePoints;
    }

    public void setReleasePoints(List<ReleasePointApptDto> releasePoints) {
        this.releasePoints = releasePoints;
    }
    
    
}
