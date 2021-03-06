/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.dto;

public class UnitMeasureCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;
    
    private String unitType;

    private Boolean efNumerator;

    private Boolean efDenominator;
    
    private Boolean unitDesignCapacity;
    
    private Boolean fuelUseUom;
    
    private String fuelUseType;
    
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

	public String getFuelUseType() {
		return fuelUseType;
	}

	public void setFuelUseType(String fuelUseType) {
		this.fuelUseType = fuelUseType;
	}
    
}
