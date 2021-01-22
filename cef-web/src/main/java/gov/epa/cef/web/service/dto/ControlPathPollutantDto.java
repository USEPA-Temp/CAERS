package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ControlPathPollutantDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Long id;
    private Long controlPathId;
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

	public Long getControlPathId() {
		return controlPathId;
	}

	public void setControlPathId(Long controlPathId) {
		this.controlPathId = controlPathId;
	}

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}
	
    public ControlPathPollutantDto withId(Long id) {
        setId(id);
        return this;
    }

}
