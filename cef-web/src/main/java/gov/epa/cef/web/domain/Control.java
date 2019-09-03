package gov.epa.cef.web.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "control")
public class Control extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code")
    private OperatingStatusCode operatingStatusCode;

    @Column(name = "identifier", nullable = false, length = 20)
    private String identifier;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "percent_capture", precision = 4, scale = 1)
    private Double percentCapture;

    @Column(name = "percent_control", precision = 4, scale = 1)
    private Double percentControl;
    
    @Column(name = "comments", length = 200)
    private String comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "control")
    private Set<ControlAssignment> assignments = new HashSet<ControlAssignment>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "control")
    private Set<ControlPollutant> pollutants = new HashSet<ControlPollutant>(0);

    /**
     * Default constructor
     */
    public Control() {}
    
    
    /**
     * Copy constructor (control assignment is not copied here, they are copied within the ControlPath entity)
     * @param originalControl
     */
    public Control(FacilitySite facilitySite, Control originalControl) {
    	this.id = originalControl.getId();
    	this.facilitySite = facilitySite;
    	this.operatingStatusCode = originalControl.getOperatingStatusCode();
    	this.identifier = originalControl.getIdentifier();
    	this.description = originalControl.getDescription();
    	this.percentCapture = originalControl.getPercentCapture();
    	this.percentControl = originalControl.getPercentControl();
    	this.comments = originalControl.getComments();
    	for (ControlPollutant pollutant : originalControl.getPollutants()) {
    		this.pollutants.add(new ControlPollutant(this, pollutant));
    	}
    }
    
    public FacilitySite getFacilitySite() {
        return facilitySite;
    }

    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public OperatingStatusCode getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentCapture() {
        return percentCapture;
    }

    public void setPercentCapture(Double percentCapture) {
        this.percentCapture = percentCapture;
    }

    public Double getPercentControl() {
        return percentControl;
    }

    public void setPercentControl(Double percentControl) {
        this.percentControl = percentControl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<ControlAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<ControlAssignment> controlAssignments) {
        this.assignments = controlAssignments;
    }

    public Set<ControlPollutant> getPollutants() {
        return pollutants;
    }

    public void setPollutants(Set<ControlPollutant> controlPollutants) {
        this.pollutants = controlPollutants;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;

    	for (ControlAssignment assignment : this.assignments) {
    		assignment.clearId();
    	}
    	
    	for (ControlPollutant pollutant : this.pollutants) {
    		pollutant.clearId();
    	}
    }

}
