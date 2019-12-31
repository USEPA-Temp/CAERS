package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ControlPollutantDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long controlId;
    private PollutantDto pollutant;
    private Double percentReduction;
    private Long facilitySiteId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public PollutantDto getPollutant() {
        return pollutant;
    }

    public void setPollutant(PollutantDto pollutant) {
        this.pollutant = pollutant;
    }

    public Double getPercentReduction() {
        return percentReduction;
    }

    public void setPercentReduction(Double percentReduction) {
        this.percentReduction = percentReduction;
    }

	public Long getControlId() {
		return controlId;
	}

	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}
	
    public ControlPollutantDto withId(Long id) {
        setId(id);
        return this;
    }
}
