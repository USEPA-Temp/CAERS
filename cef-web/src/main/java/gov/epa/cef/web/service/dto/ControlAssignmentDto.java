package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ControlAssignmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmissionsReportItemDto control;
    private ControlPathDto controlPath;
    private EmissionsProcessDto emissionsProcess;
    private EmissionsUnitDto emissionsUnit;
    private ReleasePointDto releasePoint;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmissionsReportItemDto getControl() {
        return control;
    }

    public void setControl(EmissionsReportItemDto control) {
        this.control = control;
    }

    public ControlPathDto getControlPath() {
        return controlPath;
    }

    public void setControlPath(ControlPathDto controlPath) {
        this.controlPath = controlPath;
    }

    public EmissionsProcessDto getEmissionsProcess() {
        return emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcessDto emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    public EmissionsUnitDto getEmissionsUnit() {
        return emissionsUnit;
    }

    public void setEmissionsUnit(EmissionsUnitDto emissionsUnit) {
        this.emissionsUnit = emissionsUnit;
    }

    public ReleasePointDto getReleasePoint() {
        return releasePoint;
    }

    public void setReleasePoint(ReleasePointDto releasePoint) {
        this.releasePoint = releasePoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
