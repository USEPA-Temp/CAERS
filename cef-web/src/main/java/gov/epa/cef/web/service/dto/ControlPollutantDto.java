package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ControlPollutantDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String pollutantCode;
    private String pollutantName;
    private String pollutantCasId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    public String getPollutantName() {
        return pollutantName;
    }

    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }

    public String getPollutantCasId() {
        return pollutantCasId;
    }

    public void setPollutantCasId(String pollutantCasId) {
        this.pollutantCasId = pollutantCasId;
    }

}
