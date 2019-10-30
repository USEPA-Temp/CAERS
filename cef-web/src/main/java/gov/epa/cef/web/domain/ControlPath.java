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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "control_path")
public class ControlPath extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "controlPath")
    private Set<ControlAssignment> assignments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "controlPathChild")
    private Set<ControlAssignment> childAssignments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "controlPath")
    private Set<ReleasePointAppt> releasePointAppts;


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
    	this.assignments = new HashSet<ControlAssignment>();

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

    public Set<ControlAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<ControlAssignment> assignments) {
        this.assignments = assignments;
    }

    public Set<ControlAssignment> getChildAssignments() {
        return childAssignments;
    }

    public void setChildAssignments(Set<ControlAssignment> childAssignments) {
        this.childAssignments = childAssignments;
    }

    public FacilitySite getFacilitySite() {
        return facilitySite;
    }

    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public Set<ReleasePointAppt> getReleasePointAppts() {
        return releasePointAppts;
    }

    public void setReleasePointAppts(Set<ReleasePointAppt> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
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
