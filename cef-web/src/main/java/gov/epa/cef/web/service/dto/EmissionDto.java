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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class EmissionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long reportingPeriodId;
    private PollutantDto pollutant;
    private Boolean totalManualEntry;
    private BigDecimal overallControlPercent;
    private BigDecimal totalEmissions;
    private UnitMeasureCodeDto emissionsUomCode;
    private Boolean formulaIndicator;
    private BigDecimal emissionsFactor;
    private String emissionsFactorFormula;
    private String emissionsFactorText;
    private CalculationMethodCodeDto emissionsCalcMethodCode;
    private String comments;
    private String calculationComment;
    private BigDecimal calculatedEmissionsTons;
    private UnitMeasureCodeDto emissionsNumeratorUom;
    private UnitMeasureCodeDto emissionsDenominatorUom;
    private EnergyConversionFactorDto energyConversionFactor;
    private List<EmissionFormulaVariableDto> variables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }

    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    public PollutantDto getPollutant() {
        return pollutant;
    }

    public void setPollutant(PollutantDto pollutant) {
        this.pollutant = pollutant;
    }

    public Boolean getTotalManualEntry() {
        return totalManualEntry;
    }

    public void setTotalManualEntry(Boolean totalManualEntry) {
        this.totalManualEntry = totalManualEntry;
    }

    public BigDecimal getOverallControlPercent() {
        return overallControlPercent;
    }

    public void setOverallControlPercent(BigDecimal overallControlPercent) {
        this.overallControlPercent = overallControlPercent;
    }

    public BigDecimal getTotalEmissions() {
        return totalEmissions;
    }

    public void setTotalEmissions(BigDecimal totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public UnitMeasureCodeDto getEmissionsUomCode() {
        return emissionsUomCode;
    }

    public void setEmissionsUomCode(UnitMeasureCodeDto emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    public Boolean getFormulaIndicator() {
        return formulaIndicator;
    }

    public void setFormulaIndicator(Boolean formulaIndicator) {
        this.formulaIndicator = formulaIndicator;
    }

    public BigDecimal getEmissionsFactor() {
        return emissionsFactor;
    }

    public void setEmissionsFactor(BigDecimal emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
    }

    public String getEmissionsFactorFormula() {
        return emissionsFactorFormula;
    }

    public void setEmissionsFactorFormula(String emissionsFactorFormula) {
        this.emissionsFactorFormula = emissionsFactorFormula;
    }

    public String getEmissionsFactorText() {
        return emissionsFactorText;
    }

    public void setEmissionsFactorText(String emissionsFactorText) {
        this.emissionsFactorText = emissionsFactorText;
    }

    public CalculationMethodCodeDto getEmissionsCalcMethodCode() {
        return emissionsCalcMethodCode;
    }

    public void setEmissionsCalcMethodCode(CalculationMethodCodeDto emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCalculationComment() {
        return calculationComment;
    }

    public void setCalculationComment(String calculationComment) {
        this.calculationComment = calculationComment;
    }

    public BigDecimal getCalculatedEmissionsTons() {
        return calculatedEmissionsTons;
    }

    public UnitMeasureCodeDto getEmissionsNumeratorUom() {
        return emissionsNumeratorUom;
    }

    public UnitMeasureCodeDto getEmissionsDenominatorUom() {
        return emissionsDenominatorUom;
    }

    public void setCalculatedEmissionsTons(BigDecimal calculatedEmissionsTons) {
        this.calculatedEmissionsTons = calculatedEmissionsTons;
    }

    public void setEmissionsNumeratorUom(UnitMeasureCodeDto emissionsNumeratorUom) {
        this.emissionsNumeratorUom = emissionsNumeratorUom;
    }

    public void setEmissionsDenominatorUom(UnitMeasureCodeDto emissionsDenominatorUom) {
        this.emissionsDenominatorUom = emissionsDenominatorUom;
    }

    public EnergyConversionFactorDto getEnergyConversionFactor() {
		return energyConversionFactor;
	}

	public void setEnergyConversionFactor(EnergyConversionFactorDto energyConversionFactor) {
		this.energyConversionFactor = energyConversionFactor;
	}

	public List<EmissionFormulaVariableDto> getVariables() {
        return variables;
    }

    public void setVariables(List<EmissionFormulaVariableDto> variables) {
        this.variables = variables;
    }

    public EmissionDto withId(Long id) {
        setId(id);
        return this;
    }
}
