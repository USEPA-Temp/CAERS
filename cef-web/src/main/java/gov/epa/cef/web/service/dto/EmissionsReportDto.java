package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class EmissionsReportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String eisProgramId;
    private Long masterFacilityRecordId;
    private CodeLookupDto programSystemCode;
    private Short year;
    private String status;
    private String validationStatus;
    private Boolean hasSubmitted;
    private String eisLastSubmissionStatus;
    private String fileName;
    private Boolean returnedReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEisProgramId() {
        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    public Long getMasterFacilityRecordId() {
        return masterFacilityRecordId;
    }

    public void setMasterFacilityRecordId(Long masterFacilityRecordId) {
        this.masterFacilityRecordId = masterFacilityRecordId;
    }

    public CodeLookupDto getProgramSystemCode() {
        return programSystemCode;
    }

    public void setProgramSystemCode(CodeLookupDto programSystemCode) {
        this.programSystemCode = programSystemCode;
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

	public Boolean getReturnedReport() {
		return returnedReport;
	}

	public void setReturnedReport(Boolean returnedReport) {
		this.returnedReport = returnedReport;
	}

}
