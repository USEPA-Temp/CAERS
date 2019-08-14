package gov.epa.cef.web.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "control_assignment")
public class ControlAssignment extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_id", nullable = false)
    private Control control;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "control_path_id", nullable = false)
    private ControlPath controlPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_process_id")
    private EmissionsProcess emissionsProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_unit_id")
    private EmissionsUnit emissionsUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_point_id")
    private ReleasePoint releasePoint;

    @Column(name = "description", length = 200)
    private String description;

    
    /**
     * Default constructor
     */
    public ControlAssignment() {}
    
    
    /**
     * Copy constructor
     * @param control
     * @param controlAssignment
     */
    public ControlAssignment(Control control, ControlAssignment originalControlAssignment) {
    	this.id = originalControlAssignment.getId();
    	this.control = control;
    	this.controlPath = new ControlPath(this, originalControlAssignment.getControlPath());
    	this.description = originalControlAssignment.getDescription();

    	if (originalControlAssignment.getReleasePoint() != null) {
	    	for(ReleasePoint newReleasePoint : this.control.getFacilitySite().getReleasePoints()) {
	    		if (newReleasePoint.getId().equals(originalControlAssignment.getReleasePoint().getId())) {
	    			this.releasePoint = newReleasePoint;
	    			break;
	    		}
	    	}
    	}
    	
    	if (originalControlAssignment.getEmissionsUnit() != null) {
	    	for(EmissionsUnit newEmissionsUnit : this.control.getFacilitySite().getEmissionsUnits()) {
	    		if (newEmissionsUnit.getId().equals(originalControlAssignment.getEmissionsUnit().getId())) {
	    			this.emissionsUnit = newEmissionsUnit;
	    			break;
	    		}
	    	}
    	}

    	if (originalControlAssignment.getEmissionsProcess() != null) {
	    	for(EmissionsUnit newEmissionsUnit : this.control.getFacilitySite().getEmissionsUnits()) {
	    		for (EmissionsProcess newEmissionsProcess : newEmissionsUnit.getEmissionsProcesses()) {
		    		if (newEmissionsProcess.getId().equals(originalControlAssignment.getEmissionsProcess().getId())) {
		    			this.emissionsProcess = newEmissionsProcess;
		    			break;
		    		}
	    		}
	    	}
    	}
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

    public EmissionsProcess getEmissionsProcess() {
        return emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcess emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    public EmissionsUnit getEmissionsUnit() {
        return emissionsUnit;
    }

    public void setEmissionsUnit(EmissionsUnit emissionsUnit) {
        this.emissionsUnit = emissionsUnit;
    }

    public ReleasePoint getReleasePoint() {
        return releasePoint;
    }

    public void setReleasePoint(ReleasePoint releasePoint) {
        this.releasePoint = releasePoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    	if (this.controlPath != null) {
    		this.controlPath.clearId();
    	}
    }

}
