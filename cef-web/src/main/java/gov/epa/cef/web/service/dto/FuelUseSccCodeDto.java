package gov.epa.cef.web.service.dto;

public class FuelUseSccCodeDto {
	
	private Long id;
	private CodeLookupDto sccCode;
	private CodeLookupDto calculationMaterialCode;
	private String fuelUseTypes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CodeLookupDto getSccCode() {
		return sccCode;
	}
	public void setSccCode(CodeLookupDto sccCode) {
		this.sccCode = sccCode;
	}
	public CodeLookupDto getCalculationMaterialCode() {
		return calculationMaterialCode;
	}
	public void setCalculationMaterialCode(CodeLookupDto calculationMaterialCode) {
		this.calculationMaterialCode = calculationMaterialCode;
	}
	public String getFuelUseTypes() {
		return fuelUseTypes;
	}
	public void setFuelUseTypes(String fuelUseTypes) {
		this.fuelUseTypes = fuelUseTypes;
	}
	
}
