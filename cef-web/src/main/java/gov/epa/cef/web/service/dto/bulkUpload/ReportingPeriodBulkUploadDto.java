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

@CsvFileName(name = "reporting_periods.csv")
public class ReportingPeriodBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Reporting Period ID is required.")
    private Long id;

    @NotNull(message = "Emissions Process ID is required.")
    private Long emissionsProcessId;
    
    private String emissionsProcessName;

    private String displayName;

    @NotNull(message = "Reporting Period Type Code is required.")
    @Size(max = 20, message = "Reporting Period Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String reportingPeriodTypeCode;

    @NotNull(message = "Reporting Period Operating Type Code is required.")
    @Size(max = 20, message = "Reporting Period Operating Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsOperatingTypeCode;
    
    private String emissionsOperatingTypeCodeDescription;

	@NotNull(message = "Throughput Parameter Type Code is required.")
    @Size(max = 20, message = "Throughput Parameter Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String calculationParameterTypeCode;

    private String calculationParameterTypeDescription;
	
    @NotBlank(message = "Throughput Value is required.")
    @Pattern(regexp = PositiveDecimalPattern,
        message = "Throughput Value is not in expected numeric format; found '${validatedValue}'.")
    private String calculationParameterValue;

    @NotNull(message = "Throughput Unit of Measure Code is required.")
    @Size(max = 20, message = "Throughput Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String calculationParameterUom;

    @NotNull(message = "Throughput Material Code is required.")
    @Size(max = 20, message = "Throughput Material Code can not exceed {max} chars; found '${validatedValue}'.")
    private String calculationMaterialCode;

    private String calculationMaterialDescription;
    
    @Pattern(regexp = PositiveDecimalPattern,
        message = "Fuel Value is not in expected numeric format; found '${validatedValue}'.")
    private String fuelUseValue;

    @Size(max = 20, message = "Fuel Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fuelUseUom;

    @Size(max = 20, message = "Fuel Material Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fuelUseMaterialCode;

    private String fuelUseMaterialDescription;
    
	@Pattern(regexp = PositiveDecimalPattern,
        message = "Heat Content Ratio is not in expected numeric format; found '${validatedValue}'.")
    private String heatContentValue;

    @Size(max = 20, message = "Heat Content Ratio Numerator can not exceed {max} chars; found '${validatedValue}'.")
    private String heatContentUom;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    public ReportingPeriodBulkUploadDto() {

        super(WorksheetName.ReportingPeriod);
    }

    @CsvColumn(name = "ID", order = 2)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @CsvColumn(name = "Emissions Process Name", order = 2)
    public String getEmissionsProcessName() {
		return emissionsProcessName;
	}
	public void setEmissionsProcessName(String emissionsProcessName) {
		this.emissionsProcessName = emissionsProcessName;
	}

    @CsvColumn(name = "Emissions Process ID", order = 1)
    public Long getEmissionsProcessId() {
        return emissionsProcessId;
    }
    public void setEmissionsProcessId(Long emissionsProcessId) {
        this.emissionsProcessId = emissionsProcessId;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @CsvColumn(name = "Reporting Period Type Code", order = 3)
    public String getReportingPeriodTypeCode() {
        return reportingPeriodTypeCode;
    }
    public void setReportingPeriodTypeCode(String reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    @CsvColumn(name = "Emissions Operating Type Code", order = 4)
    public String getEmissionsOperatingTypeCode() {
        return emissionsOperatingTypeCode;
    }
    public void setEmissionsOperatingTypeCode(String emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    @CsvColumn(name = "Emissions Operating Type Code Description", order = 4)
	public String getEmissionsOperatingTypeCodeDescription() {
		return emissionsOperatingTypeCodeDescription;
	}
	public void setEmissionsOperatingTypeCodeDescription(String emissionsOperatingTypeCodeDescription) {
		this.emissionsOperatingTypeCodeDescription = emissionsOperatingTypeCodeDescription;
	}

    @CsvColumn(name = "Throughput Parameter Code", order = 5)
    public String getCalculationParameterTypeCode() {
        return calculationParameterTypeCode;
    }
    public void setCalculationParameterTypeCode(String calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    @CsvColumn(name = "Throughput Parameter", order = 5)
    public String getCalculationParameterTypeDescription() {
		return calculationParameterTypeDescription;
	}
	public void setCalculationParameterTypeDescription(String calculationParameterTypeDescription) {
		this.calculationParameterTypeDescription = calculationParameterTypeDescription;
	}
    
    @CsvColumn(name = "Calculation Parameter Value", order = 6)
    public String getCalculationParameterValue() {
        return calculationParameterValue;
    }
    public void setCalculationParameterValue(String calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    @CsvColumn(name = "Calculation Parameter UOM", order = 7)
    public String getCalculationParameterUom() {
        return calculationParameterUom;
    }
    public void setCalculationParameterUom(String calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    @CsvColumn(name = "Throughput Material Code", order = 8)
    public String getCalculationMaterialCode() {
        return calculationMaterialCode;
    }
    public void setCalculationMaterialCode(String calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    @CsvColumn(name = "Throughput Material", order = 8)
	public String getCalculationMaterialDescription() {
		return calculationMaterialDescription;
	}
	public void setCalculationMaterialDescription(String calculationMaterialDescription) {
		this.calculationMaterialDescription = calculationMaterialDescription;
	}

    @CsvColumn(name = "Fuel Use Value", order = 9)
    public String getFuelUseValue() {
		return fuelUseValue;
	}
	public void setFuelUseValue(String fuelUseValue) {
		this.fuelUseValue = fuelUseValue;
	}

    @CsvColumn(name = "Fuel Use UOM", order = 10)
	public String getFuelUseUom() {
		return fuelUseUom;
	}
	public void setFuelUseUom(String fuelUseUom) {
		this.fuelUseUom = fuelUseUom;
	}

    @CsvColumn(name = "Fuel Material Code", order = 11)
	public String getFuelUseMaterialCode() {
		return fuelUseMaterialCode;
	}
	public void setFuelUseMaterialCode(String fuelUseMaterialCode) {
		this.fuelUseMaterialCode = fuelUseMaterialCode;
	}

    @CsvColumn(name = "Fuel Material", order = 11)
	public String getFuelUseMaterialDescription() {
		return fuelUseMaterialDescription;
	}
	public void setFuelUseMaterialDescription(String fuelUseMaterialDescription) {
		this.fuelUseMaterialDescription = fuelUseMaterialDescription;
	}

    @CsvColumn(name = "Heat Content Value", order = 12)
	public String getHeatContentValue() {
		return heatContentValue;
	}
	public void setHeatContentValue(String heatContentValue) {
		this.heatContentValue = heatContentValue;
	}

    @CsvColumn(name = "Heat Content UOM", order = 13)
	public String getHeatContentUom() {
		return heatContentUom;
	}
	public void setHeatContentUom(String heatContentUom) {
		this.heatContentUom = heatContentUom;
	}

    @CsvColumn(name = "Comments", order = 14)
	public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
