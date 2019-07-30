package gov.epa.cef.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pollutant")
public class Pollutant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "pollutant_code", nullable = false, length = 12)
    private String pollutantCode;
    
    @Column(name = "pollutant_name", nullable = false, length = 100)
    private String pollutantName;
    
    @Column(name = "pollutant_cas_id", length = 100)
    private String pollutantCasId;

    @Column(name = "pollutant_srs_id", nullable = false, length = 40)
    private String pollutantSrsId;

    @Column(name = "pollutant_type", nullable = false, length = 12)
    private String pollutantType;

    @Column(name = "pollutant_standard_uom_code", length = 12)
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
}
