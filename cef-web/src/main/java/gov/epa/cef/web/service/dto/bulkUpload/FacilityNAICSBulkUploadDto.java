package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class FacilityNAICSBulkUploadDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long facilitySiteId;
	private Integer code;
	private boolean primaryFlag;

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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public boolean isPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(boolean primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

}
