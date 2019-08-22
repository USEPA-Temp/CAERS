package gov.epa.cef.web.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "control_path")
public class ControlPath extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "control_order", nullable = false, length = 3)
    private String controlOrder;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "control_type", nullable = false, length = 10)
    private ControlType controlType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "controlPath")
    private ControlAssignment assignment;
    
    
    /**
     * Default constructor
     */
    public ControlPath() {}
    
    
    /**
     * Copy constructor
     * @param originalControlPath
     */
    public ControlPath(ControlAssignment assignment, ControlPath originalControlPath) {
    	this.id = originalControlPath.getId();
    	this.assignment = assignment;
    	this.description = originalControlPath.getDescription();
    	this.controlOrder = originalControlPath.getControlOrder();
    	this.controlType = originalControlPath.getControlType();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getControlOrder() {
        return controlOrder;
    }

    public void setControlOrder(String controlOrder) {
        this.controlOrder = controlOrder;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    public ControlAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(ControlAssignment assignment) {
        this.assignment = assignment;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
