package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ControlPollutantDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private PollutantDto pollutant;
    private Double percentReduction;

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
}
