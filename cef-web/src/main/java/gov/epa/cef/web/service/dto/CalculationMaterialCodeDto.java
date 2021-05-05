package gov.epa.cef.web.service.dto;

import java.math.BigDecimal;

public class CalculationMaterialCodeDto extends CodeLookupDto {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean fuelUseMaterial;
	private BigDecimal defaultHeatContentRatio;
	private UnitMeasureCodeDto heatContentRatioNumeratorUom;
	private UnitMeasureCodeDto heatContentRatioDenominatorUom;
	
	public Boolean getFuelUseMaterial() {
		return fuelUseMaterial;
	}
	public void setFuelUseMaterial(Boolean fuelUseMaterial) {
		this.fuelUseMaterial = fuelUseMaterial;
	}
	public BigDecimal getDefaultHeatContentRatio() {
		return defaultHeatContentRatio;
	}
	public void setDefaultHeatContentRatio(BigDecimal defaultHeatContentRatio) {
		this.defaultHeatContentRatio = defaultHeatContentRatio;
	}
	public UnitMeasureCodeDto getHeatContentRatioNumeratorUom() {
		return heatContentRatioNumeratorUom;
	}
	public void setHeatContentRatioNumeratorUom(
			UnitMeasureCodeDto heatContentRatioNumeratorUom) {
		this.heatContentRatioNumeratorUom = heatContentRatioNumeratorUom;
	}
	public UnitMeasureCodeDto getHeatContentRatioDenominatorUom() {
		return heatContentRatioDenominatorUom;
	}
	public void setHeatContentRatioDenominatorUom(
			UnitMeasureCodeDto heatContentRatioDenominatorUom) {
		this.heatContentRatioDenominatorUom = heatContentRatioDenominatorUom;
	}
	
}
