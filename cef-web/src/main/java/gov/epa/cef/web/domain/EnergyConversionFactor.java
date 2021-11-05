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
package gov.epa.cef.web.domain;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import gov.epa.cef.web.domain.common.BaseEntity;

@Entity
@Table(name = "energy_conversion_factor")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EnergyConversionFactor extends BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_material_code", nullable = false)
    private CalculationMaterialCode calculationMaterialCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "denominator_uom_code")
    private UnitMeasureCode emissionsDenominatorUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numerator_uom_code")
    private UnitMeasureCode emissionsNumeratorUom;
    
    @Column(name = "conversion_factor", nullable = false, precision = 14, scale = 4)
    private BigDecimal conversionFactor;
    
    @Column(name = "note", length = 2000)
    private String note;
    
    @Column(name = "source", length = 200)
    private String source;

	public CalculationMaterialCode getCalculationMaterialCode() {
		return calculationMaterialCode;
	}

	public void setCalculationMaterialCode(CalculationMaterialCode calculationMaterialCode) {
		this.calculationMaterialCode = calculationMaterialCode;
	}

	public UnitMeasureCode getEmissionsDenominatorUom() {
		return emissionsDenominatorUom;
	}

	public void setEmissionsDenominatorUom(UnitMeasureCode emissionsDenominatorUom) {
		this.emissionsDenominatorUom = emissionsDenominatorUom;
	}

	public UnitMeasureCode getEmissionsNumeratorUom() {
		return emissionsNumeratorUom;
	}

	public void setEmissionsNumeratorUom(UnitMeasureCode emissionsNumeratorUom) {
		this.emissionsNumeratorUom = emissionsNumeratorUom;
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
