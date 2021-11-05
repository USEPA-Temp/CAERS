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

import java.math.BigDecimal;

public class EnergyConversionFactorDto {
	
	private Long id;
    private UnitMeasureCodeDto emissionsNumeratorUom;
    private UnitMeasureCodeDto emissionsDenominatorUom;
    private CodeLookupDto calculationMaterialCode;
    private BigDecimal conversionFactor;
    private String note;
    private String source;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UnitMeasureCodeDto getEmissionsNumeratorUom() {
		return emissionsNumeratorUom;
	}
	public void setEmissionsNumeratorUom(UnitMeasureCodeDto emissionsNumeratorUom) {
		this.emissionsNumeratorUom = emissionsNumeratorUom;
	}
	public UnitMeasureCodeDto getEmissionsDenominatorUom() {
		return emissionsDenominatorUom;
	}
	public void setEmissionsDenominatorUom(UnitMeasureCodeDto emissionsDenominatorUom) {
		this.emissionsDenominatorUom = emissionsDenominatorUom;
	}
	public CodeLookupDto getCalculationMaterialCode() {
		return calculationMaterialCode;
	}
	public void setCalculationMaterialCode(CodeLookupDto calculationMaterialCode) {
		this.calculationMaterialCode = calculationMaterialCode;
	}
	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}
	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
