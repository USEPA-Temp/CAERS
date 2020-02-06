package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class ControlPollutantBulkUploadDto implements IWorksheetAware, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long controlId;
    private String pollutantCode;
    private Double percentReduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getControlId() {
        return controlId;
    }

    public void setControlId(Long controlId) {
        this.controlId = controlId;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutant) {
        this.pollutantCode = pollutant;
    }

    public Double getPercentReduction() {
        return percentReduction;
    }

    public void setPercentReduction(Double percentReduction) {
        this.percentReduction = percentReduction;
    }

}
