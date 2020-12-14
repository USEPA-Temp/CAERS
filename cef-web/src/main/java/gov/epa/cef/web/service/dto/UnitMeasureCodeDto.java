package gov.epa.cef.web.service.dto;

public class UnitMeasureCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;
    
    private String unitType;

    private Boolean efNumerator;

    private Boolean efDenominator;
    
    private Boolean unitDesignCapacity;
    
    private Boolean fuelEfDenominator;
    
    private Boolean heatContentUnit;

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

    public Boolean getFuelEfDenominator() {
		return fuelEfDenominator;
	}

	public void setFuelEfDenominator(Boolean fuelEfDenominator) {
		this.fuelEfDenominator = fuelEfDenominator;
	}

	public Boolean getHeatContentUnit() {
		return heatContentUnit;
	}

	public void setHeatContentUnit(Boolean heatContentUnit) {
		this.heatContentUnit = heatContentUnit;
	}

	public Boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }


}
