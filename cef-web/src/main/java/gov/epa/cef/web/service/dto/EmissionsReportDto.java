package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class EmissionsReportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String frsFacilityId;
    private String eisProgramId;
    private String agencyCode;
    private Short year;
    private String status;
    private String validationStatus;
    private Boolean hasSubmitted;
    private String eisLastSubmissionStatus;
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrsFacilityId() {
        return frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    public String getEisProgramId() {
        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

	public Boolean getHasSubmitted() {
		return hasSubmitted;
	}

	public void setHasSubmitted(Boolean hasSubmitted) {
		this.hasSubmitted = hasSubmitted;
	}
	
	public String getEisLastSubmissionStatus() {
		return eisLastSubmissionStatus;
	}

	public void setEisLastSubmissionStatus(String eisLastSubmissionStatus) {
		this.eisLastSubmissionStatus = eisLastSubmissionStatus;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public EmissionsReportDto withId(Long id) {
        setId(id);
        return this;
    }


}
