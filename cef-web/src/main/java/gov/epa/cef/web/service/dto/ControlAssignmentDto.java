package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class ControlAssignmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ControlDto control;
    private ControlDto controlPath;
    private Integer sequenceNumber;
    private ControlPathDto controlPathChild;
    private BigDecimal percentApportionment;
    private Long facilitySiteId;
    
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

	public BigDecimal getPercentApportionment() {
		return percentApportionment;
	}

	public void setPercentApportionment(BigDecimal percentApportionment) {
		this.percentApportionment = percentApportionment;
	}

	public Long getFacilitySiteId() {
		return facilitySiteId;
	}

	public void setFacilitySiteId(Long facilitySiteId) {
		this.facilitySiteId = facilitySiteId;
	}

	public ControlDto getControlPath() {
		return controlPath;
	}

	public void setControlPath(ControlDto controlPath) {
		this.controlPath = controlPath;
	}
    public ControlAssignmentDto withId(Long id) {
        setId(id);
        return this;
    }

}
