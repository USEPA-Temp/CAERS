package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class PollutantDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String pollutantCode;
    private String pollutantName;
    private String pollutantCasId;
    private String pollutantSrsId;
    private String pollutantType;
    private String pollutantStandardUomCode;
    
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
    public String getPollutantSrsId() {
        return pollutantSrsId;
    }
    public void setPollutantSrsId(String pollutantSrsId) {
        this.pollutantSrsId = pollutantSrsId;
    }
    public String getPollutantType() {
        return pollutantType;
    }
    public void setPollutantType(String pollutantType) {
        this.pollutantType = pollutantType;
    }
    public String getPollutantStandardUomCode() {
        return pollutantStandardUomCode;
    }
    public void setPollutantStandardUomCode(String pollutantStandardUomCode) {
        this.pollutantStandardUomCode = pollutantStandardUomCode;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
