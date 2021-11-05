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
package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import gov.epa.cef.web.annotation.CsvColumn;
import gov.epa.cef.web.annotation.CsvFileName;

import java.io.Serializable;

@CsvFileName(name = "emissions.csv")
public class EmissionBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Emission ID is required.")
    private Long id;

    @NotNull(message = "Report Period is required.")
    private Long reportingPeriodId;

    private String displayName;

    @NotBlank(message = "Pollutant Code is required.")
    @Size(max = 12, message = "Pollutant Code can not exceed {max} chars; found '${validatedValue}'.")
    private String pollutantCode;
    
    private String pollutantName;

    private boolean totalManualEntry;

    @Pattern(regexp = "^\\d{0,2}(\\.\\d{1,6})?$",
        message = "Overall Control Percent is not in expected numeric format: '{2}.{6}' digits; found '${validatedValue}'.")
    private String overallControlPercent;

    @NotBlank(message = "Total Emissions is required.")
    @Pattern(regexp = PositiveDecimalPattern,
        message = "Total Emissions is not in expected numeric format; found '${validatedValue}'.")
    private String totalEmissions;

    @NotBlank(message = "Emissions Unit of Measure is required.")
    @Size(max = 20, message = "Emissions Unit of Measure can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsUomCode;

    @Pattern(regexp = PositiveDecimalPattern,
        message = "Emissions Factor is not in expected numeric format; found '${validatedValue}'.")
    private String emissionsFactor;

    @Size(max = 100, message = "Emissions Factor Formula can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsFactorFormula;

    @Size(max = 100, message = "Emissions Factor Text can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsFactorText;

    @NotBlank(message = "Emissions Calculation Method is required.")
    @Size(max = 20, message = "Emissions Calculation Method can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsCalcMethodCode;
    
    private String emissionsCalcMethodDescription;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @Size(max = 4000, message = "Description of Calculation can not exceed {max} chars; found '${validatedValue}'.")
    private String calculationComment;

    @Size(max = 20, message = "Emissions Numerator UoM Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsNumeratorUom;

    @Size(max = 20, message = "Emissions Denominator UoM Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsDenominatorUom;

    @Pattern(regexp = PositiveDecimalPattern,
        message = "Calculated Emissions Tons is not in expected numeric format; found '${validatedValue}'.")
    private String calculatedEmissionsTons;

    public EmissionBulkUploadDto() {

        super(WorksheetName.Emission);
    }

    @CsvColumn(name = "ID", order = 9)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @CsvColumn(name = "Reporting Period ID", order = 1)
    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }
    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @CsvColumn(name = "Pollutant Code", order = 2)
    public String getPollutantCode() {
        return pollutantCode;
    }
    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    @CsvColumn(name = "Pollutant Name", order = 2)
    public String getPollutantName() {
        return pollutantName;
    }
    public void setPollutantName(String pollutantName) {
        this.pollutantName = pollutantName;
    }

    @CsvColumn(name = "Is Total Manual Entry", order = 3)
    public boolean isTotalManualEntry() {
        return totalManualEntry;
    }
    public void setTotalManualEntry(boolean totalManualEntry) {
        this.totalManualEntry = totalManualEntry;
    }

    @CsvColumn(name = "Overall Control Percent", order = 6)
    public String getOverallControlPercent() {
        return overallControlPercent;
    }
    public void setOverallControlPercent(String overallControlPercent) {
        this.overallControlPercent = overallControlPercent;
    }

    @CsvColumn(name = "Total Emissions", order = 4)
    public String getTotalEmissions() {
        return totalEmissions;
    }
    public void setTotalEmissions(String totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    @CsvColumn(name = "Emissions UOM", order = 5)
    public String getEmissionsUomCode() {
        return emissionsUomCode;
    }
    public void setEmissionsUomCode(String emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    @CsvColumn(name = "Emissions Factor", order = 7)
    public String getEmissionsFactor() {
        return emissionsFactor;
    }
    public void setEmissionsFactor(String emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
    }

    @CsvColumn(name = "Emissions Factor Text", order = 8)
    public String getEmissionsFactorText() {
        return emissionsFactorText;
    }
    public void setEmissionsFactorText(String emissionsFactorText) {
        this.emissionsFactorText = emissionsFactorText;
    }

    @CsvColumn(name = "Emissions Calc Method Code", order = 11)
    public String getEmissionsCalcMethodCode() {
        return emissionsCalcMethodCode;
    }
    public void setEmissionsCalcMethodCode(String emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
    }

    @CsvColumn(name = "Emissions Calc Method Description", order = 11)
    public String getEmissionsCalcMethodDescription() {
    	return emissionsCalcMethodDescription;
    }
    public void setEmissionsCalcMethodDescription(String emissionsCalcMethodDescription) {
    	this.emissionsCalcMethodDescription = emissionsCalcMethodDescription;
    }

    @CsvColumn(name = "Comment", order = 15)
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @CsvColumn(name = "Calculation Comment", order = 14)
    public String getCalculationComment() {
        return calculationComment;
    }
    public void setCalculationComment(String calculationComment) {
        this.calculationComment = calculationComment;
    }

    public String getCalculatedEmissionsTons() {
        return calculatedEmissionsTons;
    }
    public void setCalculatedEmissionsTons(String calculatedEmissionsTons) {
        this.calculatedEmissionsTons = calculatedEmissionsTons;
    }

    @CsvColumn(name = "Emissions Numerator UOM", order = 12)
    public String getEmissionsNumeratorUom() {
        return emissionsNumeratorUom;
    }
    public void setEmissionsNumeratorUom(String emissionsNumeratorUom) {
        this.emissionsNumeratorUom = emissionsNumeratorUom;
    }

    @CsvColumn(name = "Emissions Denominator UOM", order = 13)
    public String getEmissionsDenominatorUom() {
        return emissionsDenominatorUom;
    }
    public void setEmissionsDenominatorUom(String emissionsDenominatorUom) {
        this.emissionsDenominatorUom = emissionsDenominatorUom;
    }

    @CsvColumn(name = "Emissions Factor Formula", order = 10)
    public String getEmissionsFactorFormula() {

        return emissionsFactorFormula;
    }

    public void setEmissionsFactorFormula(String emissionsFactorFormula) {

        this.emissionsFactorFormula = emissionsFactorFormula;
    }
}
