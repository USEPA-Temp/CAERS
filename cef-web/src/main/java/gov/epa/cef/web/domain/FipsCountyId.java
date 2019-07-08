package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FipsCountyId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class FipsCountyId implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields
    
    @Column(name = "code", nullable = false, length = 3)
    private String code;
    
    @Column(name = "state_code", nullable = false, length = 2)
    private String stateCode;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof FipsCountyId)) {
            return false;
        }
        
        FipsCountyId castOther = (FipsCountyId) other;

        return ((this.getCode() == castOther.getCode()) || (this.getCode() != null && castOther.getCode() != null
                && this.getCode().equals(castOther.getCode())))
                && ((this.getStateCode() == castOther.getStateCode()) || (this.getStateCode() != null
                        && castOther.getStateCode() != null && this.getStateCode().equals(castOther.getStateCode())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getCode() == null ? 0 : this.getCode().hashCode());
        result = 37 * result + (getStateCode() == null ? 0 : this.getStateCode().hashCode());
        return result;
    }

}