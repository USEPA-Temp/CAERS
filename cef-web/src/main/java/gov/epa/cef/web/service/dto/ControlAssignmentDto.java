package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ControlAssignmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EmissionsReportItemDto control;
    private ControlPathDto controlPath;
    private String description;
    private Integer sequenceNumber;
    private ControlPathDto controlPathChild;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public ControlPathDto getControlPathChild() {
        return controlPathChild;
    }

    public void setControlPathChild(ControlPathDto controlPathChild) {
        this.controlPathChild = controlPathChild;
    }

}
