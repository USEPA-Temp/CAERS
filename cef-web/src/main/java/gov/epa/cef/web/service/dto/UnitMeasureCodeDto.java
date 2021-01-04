package gov.epa.cef.web.service.dto;

public class UnitMeasureCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;
    
    private String unitType;

    private Boolean efNumerator;

    private Boolean efDenominator;
    
    private Boolean unitDesignCapacity;
    
    private Boolean fuelUseUom;
    
    private Boolean heatContentUom;

    private Boolean legacy;

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Boolean getEfNumerator() {
        return efNumerator;
    }

    public void setEfNumerator(Boolean efNumerator) {
        this.efNumerator = efNumerator;
    }

    public Boolean getEfDenominator() {
        return efDenominator;
    }

    public void setEfDenominator(Boolean efDenominator) {
        this.efDenominator = efDenominator;
    }
    
    public Boolean getUnitDesignCapacity() {
        return unitDesignCapacity;
    }

    public void setUnitDesignCapacity(Boolean unitDesignCapacity) {
        this.unitDesignCapacity = unitDesignCapacity;
    }

	public Boolean getFuelUseUom() {
		return fuelUseUom;
	}

	public void setFuelUseUom(Boolean fuelUseUom) {
		this.fuelUseUom = fuelUseUom;
	}

	public Boolean getHeatContentUom() {
		return heatContentUom;
	}

	public void setHeatContentUom(Boolean heatContentUom) {
		this.heatContentUom = heatContentUom;
	}

	public Boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

}
