package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "control_assignment")
public class ControlAssignment extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_id")
    private Control control;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_path_id", nullable = false)
    private ControlPath controlPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_path_child_id")
    private ControlPath controlPathChild;
    
    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;
    
    @Column(name = "percent_apportionment", nullable = false, precision = 5, scale = 2)
    private Double percentApportionment;

    
    /**
     * Default constructor
     */
    public ControlAssignment() {}
    
    
    /**
     * Copy constructor
     * @param control
     * @param controlAssignment
     */
    public ControlAssignment(ControlPath controlPath, Control newControl, ControlPath newControlPathChild, ControlAssignment originalControlAssignment) {
    	this.id = originalControlAssignment.getId();
    	this.controlPath = controlPath;
    	this.controlPathChild = newControlPathChild;
    	this.control = newControl;
        this.sequenceNumber = originalControlAssignment.getSequenceNumber();
        this.percentApportionment = originalControlAssignment.getPercentApportionment();
    }
    
    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public ControlPath getControlPath() {
        return controlPath;
    }

    public void setControlPath(ControlPath controlPath) {
        this.controlPath = controlPath;
    }

    public ControlPath getControlPathChild() {
        return controlPathChild;
    }

    public void setControlPathChild(ControlPath controlPathChild) {
        this.controlPathChild = controlPathChild;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Double getPercentApportionment() {
        return this.percentApportionment;
    }

    public void setPercentApportionment(Double percentApportionment) {
        this.percentApportionment = percentApportionment;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
