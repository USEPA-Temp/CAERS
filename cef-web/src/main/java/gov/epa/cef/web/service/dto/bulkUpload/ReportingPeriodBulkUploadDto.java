package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ReportingPeriodBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Reporting Period ID is required.")
    private Long id;

    @NotNull(message = "Emissions Process ID is required.")
    private Long emissionsProcessId;

    private String displayName;

    @NotNull(message = "Reporting Period Type Code is required.")
    @Size(max = 20, message = "Reporting Period Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String reportingPeriodTypeCode;

    @NotNull(message = "Reporting Period Operating Type Code is required.")
    @Size(max = 20, message = "Reporting Period Operating Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsOperatingTypeCode;

    @NotNull(message = "Throughput Parameter Type Code is required.")
    @Size(max = 20, message = "Throughput Parameter Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String calculationParameterTypeCode;

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
    
    @Pattern(regexp = PositiveDecimalPattern,
        message = "Fuel Use Value is not in expected numeric format; found '${validatedValue}'.")
    private String fuelUseValue;

    @Size(max = 20, message = "Fuel Use Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fuelUseUom;

    @Size(max = 20, message = "Fuel Use Material Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fuelUseMaterialCode;
    
    @Pattern(regexp = PositiveDecimalPattern,
        message = "Heat Content Value is not in expected numeric format; found '${validatedValue}'.")
    private String heatContentValue;

    @Size(max = 20, message = "Heat Content Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String heatContentUom;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    public ReportingPeriodBulkUploadDto() {

        super(WorksheetName.ReportingPeriod);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getReportingPeriodTypeCode() {
        return reportingPeriodTypeCode;
    }
    public void setReportingPeriodTypeCode(String reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public String getEmissionsOperatingTypeCode() {
        return emissionsOperatingTypeCode;
    }
    public void setEmissionsOperatingTypeCode(String emissionsOperatingTypeCode) {
        this.emissionsOperatingTypeCode = emissionsOperatingTypeCode;
    }

    public String getCalculationParameterTypeCode() {
        return calculationParameterTypeCode;
    }
    public void setCalculationParameterTypeCode(String calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public String getCalculationParameterValue() {
        return calculationParameterValue;
    }
    public void setCalculationParameterValue(String calculationParameterValue) {
        this.calculationParameterValue = calculationParameterValue;
    }

    public String getCalculationParameterUom() {
        return calculationParameterUom;
    }
    public void setCalculationParameterUom(String calculationParameterUom) {
        this.calculationParameterUom = calculationParameterUom;
    }

    public String getCalculationMaterialCode() {
        return calculationMaterialCode;
    }
    public void setCalculationMaterialCode(String calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }

    public String getFuelUseValue() {
		return fuelUseValue;
	}
	public void setFuelUseValue(String fuelUseValue) {
		this.fuelUseValue = fuelUseValue;
	}

	public String getFuelUseUom() {
		return fuelUseUom;
	}
	public void setFuelUseUom(String fuelUseUom) {
		this.fuelUseUom = fuelUseUom;
	}

	public String getFuelUseMaterialCode() {
		return fuelUseMaterialCode;
	}
	public void setFuelUseMaterialCode(String fuelUseMaterialCode) {
		this.fuelUseMaterialCode = fuelUseMaterialCode;
	}

	public String getHeatContentValue() {
		return heatContentValue;
	}
	public void setHeatContentValue(String heatContentValue) {
		this.heatContentValue = heatContentValue;
	}

	public String getHeatContentUom() {
		return heatContentUom;
	}
	public void setHeatContentUom(String heatContentUom) {
		this.heatContentUom = heatContentUom;
	}

	public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
