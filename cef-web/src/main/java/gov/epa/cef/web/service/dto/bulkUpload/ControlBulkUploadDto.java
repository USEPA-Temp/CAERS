package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ControlBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @NotBlank(message = "Control Measure Code is required.")
    @Size(max = 20, message = "Control Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String controlMeasureCode;

    @NotBlank(message = "Description is required.")
    @Size(max = 200, message = "Description can not exceed {max} chars.")
    private String description;

    @NotNull(message = "Facility Site ID is required.")
    private Long facilitySiteId;

    @NotNull(message = "Control ID is required")
    private Long id;

    @NotBlank(message = "Control Identifier is required.")
    @Size(max = 20, message = "Control Identifier can not exceed {max} chars.")
    private String identifier;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars.")
    private String operatingStatusCode;
    
    @Pattern(regexp = YearPattern,
        message = "Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    // field has become legacy with CEF-984. Data is no longer saved in db.
    // field remains to prevent error when uploading older version of template.
    @Pattern(regexp = PercentPattern,
        message = "Percent Capture is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentCapture;

	@Pattern(regexp = "^\\d{0,3}(\\.\\d)?$",
        message = "Percent Control Effectiveness is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentControl;
    
    private String numberOperatingMonths;
    
    private String startDate;
    
    private String upgradeDate;
    
    private String endDate;
    
    @Size(max = 200, message = "Description can not exceed {max} chars.")
    private String upgradeDescription;

    public ControlBulkUploadDto() {

        super(WorksheetName.Control);
    }

    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

    public String getControlMeasureCode() {

        return controlMeasureCode;
    }

    public void setControlMeasureCode(String controlMeasureCode) {

        this.controlMeasureCode = controlMeasureCode;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Long getFacilitySiteId() {

        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {

        this.facilitySiteId = facilitySiteId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getIdentifier() {

        return identifier;
    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    public String getOperatingStatusCode() {

        return operatingStatusCode;
    }

    public void setOperatingStatusCode(String operatingStatusCode) {

        this.operatingStatusCode = operatingStatusCode;
    }

    public String getPercentCapture() {

        return percentCapture;
    }

    public void setPercentCapture(String percentCapture) {

        this.percentCapture = percentCapture;
    }

    public String getPercentControl() {

        return percentControl;
    }

    public void setPercentControl(String percentControl) {

        this.percentControl = percentControl;
    }

	public String getNumberOperatingMonths() {
		return numberOperatingMonths;
	}

	public void setNumberOperatingMonths(String numberOperatingMonths) {
		this.numberOperatingMonths = numberOperatingMonths;
	}

	public String getUpgradeDescription() {
		return upgradeDescription;
	}

	public void setUpgradeDescription(String upgradeDescription) {
		this.upgradeDescription = upgradeDescription;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(String upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatusYear() {
		return statusYear;
	}

	public void setStatusYear(String statusYear) {
		this.statusYear = statusYear;
	}

}
