package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

public class ControlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long facilitySiteId;
    private CodeLookupDto operatingStatusCode;
    private String identifier;
    private String description;
    private Double percentCapture;
    private Double percentControl;
    private List<ControlPollutantDto> pollutants;
    private String comments;
    private CodeLookupDto controlMeasureCode;

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

    public CodeLookupDto getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(CodeLookupDto operatingStatusCode) {
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

    public List<ControlPollutantDto> getPollutants() {
        return pollutants;
    }

    public void setPollutants(List<ControlPollutantDto> pollutants) {
        this.pollutants = pollutants;
    }
    
    public CodeLookupDto getControlMeasureCode() {
      return controlMeasureCode;
  }

  public void setControlMeasureCode(CodeLookupDto controlMeasureCode) {
      this.controlMeasureCode = controlMeasureCode;
  }
  
    public ControlDto withId(Long id) {
        setId(id);
        return this;
    }

}
