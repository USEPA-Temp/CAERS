package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionsByFacilityAndCASDto implements Serializable {
    
    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;
    
    private String message;
    private String code;
    private String frsFacilityId;
    private String facilityName;
    private Short year;
    private String chemical;
    private String casNumber;
    private BigDecimal stackEmissions;
    private BigDecimal fugitiveEmissions;
    private String uom;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getFrsFacilityId() {
        return frsFacilityId;
    }
    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }
    public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public Short getYear() {
        return year;
    }
    public void setYear(Short year) {
        this.year = year;
    }
    public String getChemical() {
        return chemical;
    }
    public void setChemical(String chemical) {
        this.chemical = chemical;
    }
    public String getCasNumber() {
        return casNumber;
    }
    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }
    public BigDecimal getStackEmissions() {
        return stackEmissions;
    }
    public void setStackEmissions(BigDecimal stackEmissions) {
        this.stackEmissions = stackEmissions;
    }
    public BigDecimal getFugitiveEmissions() {
        return fugitiveEmissions;
    }
    public void setFugitiveEmissions(BigDecimal fugitiveEmissions) {
        this.fugitiveEmissions = fugitiveEmissions;
    }
    public String getUom() {
        return uom;
    }
    public void setUom(String uom) {
        this.uom = uom;
    }
}
