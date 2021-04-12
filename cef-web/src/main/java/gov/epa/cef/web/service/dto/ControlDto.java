package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ControlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long facilitySiteId;
    private CodeLookupDto operatingStatusCode;
    private Short statusYear;
    private String identifier;
    private String description;
    private String upgradeDescription;
    private Short numberOperatingMonths;
    private Double percentCapture;
    private Double percentControl;
    private LocalDate startDate;
    private LocalDate upgradeDate;
    private LocalDate endDate;
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
  
    public String getUpgradeDescription() {
    	return upgradeDescription;
	}
	
	public void setUpgradeDescription(String upgradeDescription) {
		this.upgradeDescription = upgradeDescription;
	}
	
	public Short getNumberOperatingMonths() {
		return numberOperatingMonths;
	}
	
	public void setNumberOperatingMonths(Short numberOperatingMonths) {
		this.numberOperatingMonths = numberOperatingMonths;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getUpgradeDate() {
		return upgradeDate;
	}
	
	public void setUpgradeDate(LocalDate upgradeDate) {
		this.upgradeDate = upgradeDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Short getStatusYear() {
		return statusYear;
	}

	public void setStatusYear(Short statusYear) {
		this.statusYear = statusYear;
	}

	public ControlDto withId(Long id) {
        setId(id);
        return this;
    }

}
