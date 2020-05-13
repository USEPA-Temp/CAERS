package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class FacilityNAICSBulkUploadDto extends BaseWorksheetDto implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotNull(message = "NAICS ID is required.")
	private Long id;

    @NotNull(message = "Facility Site ID is required.")
	private Long facilitySiteId;

    @NotBlank(message = "NAICS code is required.")
    @Pattern(regexp = "^\\d{0,6}$",
        message = "NAICS is not in expected numeric format; found '${validatedValue}'.")
	private String code;

    // Object type boolean is used here only to trigger @NotNull validation, primitive type boolean should be used to only allow true/false values.
    @NotNull(message = "Primary Flag is required.")
	private Boolean primaryFlag;

    public FacilityNAICSBulkUploadDto() {

        super(WorksheetName.FacilityNaics);
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(boolean primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

}
