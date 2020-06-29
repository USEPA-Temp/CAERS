package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class FipsCountyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String countyCode;
    private FipsStateCodeDto fipsStateCode;
    private String name;
    private Integer lastInventoryYear;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public FipsStateCodeDto getFipsStateCode() {
        return fipsStateCode;
    }

    public void setFipsStateCode(FipsStateCodeDto fipsStateCode) {
        this.fipsStateCode = fipsStateCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getLastInventoryYear() {
        return lastInventoryYear;
    }

    public void setLastInventoryYear(Integer lastInventoryYear) {
        this.lastInventoryYear = lastInventoryYear;
    }

}
