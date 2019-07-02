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
    private BigDecimal pointEmissions;
    private BigDecimal nonPointEmissions;
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
    public BigDecimal getPointEmissions() {
        return pointEmissions;
    }
    public void setPointEmissions(BigDecimal pointEmissions) {
        this.pointEmissions = pointEmissions;
    }
    public BigDecimal getNonPointEmissions() {
        return nonPointEmissions;
    }
    public void setNonPointEmissions(BigDecimal nonPointEmissions) {
        this.nonPointEmissions = nonPointEmissions;
    }
    public String getUom() {
        return uom;
    }
    public void setUom(String uom) {
        this.uom = uom;
    }
}
