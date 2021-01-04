package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class PointSourceSccCodeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private Short lastInventoryYear;
	private Boolean fuelUseRequired;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Short getLastInventoryYear() {
		return lastInventoryYear;
	}
	
	public void setLastInventoryYear(Short lastInventoryYear) {
		this.lastInventoryYear = lastInventoryYear;
	}
	
	public Boolean getFuelUseRequired() {
		return fuelUseRequired;
	}
	  
	public void setFuelUseRequired(Boolean fuelUseRequired) {
		this.fuelUseRequired = fuelUseRequired;
	}

}
