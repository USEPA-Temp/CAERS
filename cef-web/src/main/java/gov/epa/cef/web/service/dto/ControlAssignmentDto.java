package gov.epa.cef.web.service.dto;

import java.io.Serializable;


public class ControlAssignmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ControlDto control;
    private Integer sequenceNumber;
    private ControlPathDto controlPathChild;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ControlDto getControl() {
        return control;
    }

    public void setControl(ControlDto control) {
        this.control = control;
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
