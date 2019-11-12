package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "control_path")
public class ControlPath extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "controlPath")
    private List<ControlAssignment> assignments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "controlPathChild")
    private List<ControlAssignment> childAssignments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "controlPath")
    private List<ReleasePointAppt> releasePointAppts = new ArrayList<>();


    /**
     * Default constructor
     */
    public ControlPath() {}


    /**
     * Copy constructor
     * @param originalControlPath
     */
    public ControlPath(FacilitySite facilitySite, ControlPath originalControlPath) {
    	this.id = originalControlPath.getId();
    	this.facilitySite = facilitySite;
    	this.description = originalControlPath.getDescription();
//    	this.assignments = new HashSet<ControlAssignment>();

        for (ControlAssignment originalControlAssignment : originalControlPath.getAssignments()) {
        	Control c = null;
        	for(Control newControl : this.facilitySite.getControls()) {
        		if (newControl.getId().equals(originalControlAssignment.getControl().getId())) {
        			c = newControl;
        			break;
        		}
        	}
        	ControlPath cpc = null;
        	//if the original control assignment has a child control path, then loop through the
        	//control paths associated with the new facility, find the appropriate one, and
        	//associate it to this control assignment - otherwise leave child path as null
        	if (originalControlAssignment.getControlPathChild() != null) {
            	for(ControlPath newControlPathChild : this.facilitySite.getControlPaths()) {
            		if (newControlPathChild.getId().equals(originalControlAssignment.getControlPathChild().getId())) {
            			cpc = newControlPathChild;
            			break;
            		}
            	}
        	}
        	this.assignments.add(new ControlAssignment(this, c, cpc, originalControlAssignment));
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ControlAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ControlAssignment> assignments) {

        this.assignments.clear();
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
    }

    public List<ControlAssignment> getChildAssignments() {
        return childAssignments;
    }

    public void setChildAssignments(List<ControlAssignment> childAssignments) {

        this.childAssignments.clear();
        if (childAssignments != null) {
            this.childAssignments.addAll(childAssignments);
        }
    }

    public FacilitySite getFacilitySite() {
        return facilitySite;
    }

    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public List<ReleasePointAppt> getReleasePointAppts() {
        return releasePointAppts;
    }

    public void setReleasePointAppts(List<ReleasePointAppt> releasePointAppts) {

        this.releasePointAppts.clear();
        if (releasePointAppts != null) {
            this.releasePointAppts.addAll(releasePointAppts);
        }
    }


    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;

    	//clear the ids for the child control assignments
        for (ControlAssignment controlAssignment : this.assignments) {
            controlAssignment.clearId();
        }
    }

}
