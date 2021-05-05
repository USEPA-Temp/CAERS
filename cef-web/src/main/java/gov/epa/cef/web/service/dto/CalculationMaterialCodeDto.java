package gov.epa.cef.web.service.dto;

import java.math.BigDecimal;

import gov.epa.cef.web.domain.UnitMeasureCode;

public class CalculationMaterialCodeDto extends CodeLookupDto {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean fuelUseMaterial;
	private BigDecimal defaultHeatContentRatio;
	private UnitMeasureCode heatContentRatioNumeratorUom;
	private UnitMeasureCode heatContentRatioDenominatorUom;
	
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
	public UnitMeasureCode getHeatContentRatioNumeratorUom() {
		return heatContentRatioNumeratorUom;
	}
	public void setHeatContentRatioNumeratorUom(
			UnitMeasureCode heatContentRatioNumeratorUom) {
		this.heatContentRatioNumeratorUom = heatContentRatioNumeratorUom;
	}
	public UnitMeasureCode getHeatContentRatioDenominatorUom() {
		return heatContentRatioDenominatorUom;
	}
	public void setHeatContentRatioDenominatorUom(
			UnitMeasureCode heatContentRatioDenominatorUom) {
		this.heatContentRatioDenominatorUom = heatContentRatioDenominatorUom;
	}
	
}
