package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ControlPathBulkUploadDto  extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Control Path ID is required.")
    private Long id;

    @NotNull(message = "Facility Site ID is required.")
    private Long facilitySiteId;

    @NotBlank(message = "Description is required.")
    @Size(max = 200, message = "Description can not exceed {max} chars; found ${validatedValue}.")
    private String description;

    @NotBlank(message = "Path ID is required.")
    @Size(max = 20, message = "Path ID can not exceed {max} chars; found ${validatedValue}.")
    private String pathId;
    
    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,3})?$",
        message = "Percent Control Effectiveness is not in expected numeric format: '{3}.{3}' digits; found '${validatedValue}'.")
    private String percentControl;

    public ControlPathBulkUploadDto() {

        super(WorksheetName.ControlPath);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilitySiteId() {
        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getPathId() {
		return pathId;
	}

	public void setPathId(String pathId) {
		this.pathId = pathId;
	}
	
	public String getPercentControl() {

        return percentControl;
    }

    public void setPercentControl(String percentControl) {

        this.percentControl = percentControl;
    }

}
