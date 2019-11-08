package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class ControlBulkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long facilitySiteId;
    private String operatingStatusCode;
    private String identifier;
    private String description;
    private Double percentCapture;
    private Double percentControl;
    private String comments;
    private String controlMeasureCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilitySiteId() {
        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
    }

    public String getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(String operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentCapture() {
        return percentCapture;
    }

    public void setPercentCapture(Double percentCapture) {
        this.percentCapture = percentCapture;
    }

    public Double getPercentControl() {
        return percentControl;
    }

    public void setPercentControl(Double percentControl) {
        this.percentControl = percentControl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getControlMeasureCode() {
        return controlMeasureCode;
    }

    public void setControlMeasureCode(String controlMeasureCode) {
        this.controlMeasureCode = controlMeasureCode;
    }

}
