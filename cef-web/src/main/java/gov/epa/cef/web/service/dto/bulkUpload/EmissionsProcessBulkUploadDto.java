package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class EmissionsProcessBulkUploadDto implements Serializable {

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long emissionsUnitId;
    private String emissionsProcessIdentifier;
    private String operatingStatusCode;
    private Short statusYear;
    private String sccCode;
    private String sccShortName;
    private String description;
    private String aircraftEngineTypeCode;
    private String comments;
    private String sccDescription;

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

    public String getAircraftEngineTypeCode() {
        return aircraftEngineTypeCode;
    }
    public void setAircraftEngineTypeCode(String aircraftEngineTypeCode) {
        this.aircraftEngineTypeCode = aircraftEngineTypeCode;
    }

    public String getOperatingStatusCode() {
        return operatingStatusCode;
    }
    public void setOperatingStatusCode(String operatingStatusCode) {
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

    public String getSccDescription() {
        return sccDescription;
    }
    public void setSccDescription(String sccDescription) {
        this.sccDescription = sccDescription;
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
}

