package gov.epa.cef.web.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    
    @Column(name = "upgraded_description", length = 200)
    private String upgradedDescription;

    @Column(name = "percent_capture", precision = 4, scale = 1)
    private Double percentCapture;

    @Column(name = "percent_control", precision = 6, scale = 3)
    private Double percentControl;
    
    @Column(name = "number_operating_months", precision = 2, scale = 0)
    private Short numberOperatingMonths;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "upgraded_date", nullable = false)
    private LocalDate upgradedDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "comments", length = 400)
    private String comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "control")
    private List<ControlAssignment> assignments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "control")
    private List<ControlPollutant> pollutants = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_measure_code", nullable = false)
    private ControlMeasureCode controlMeasureCode;

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
    	this.percentControl = originalControl.getPercentControl();
    	this.upgradedDescription = originalControl.getUpgradedDescription();
    	this.numberOperatingMonths = originalControl.getNumberOperatingMonths();
    	this.startDate = originalControl.getStartDate();
    	this.upgradedDate = originalControl.getUpgradedDate();
    	this.endDate = originalControl.getEndDate();
    	this.comments = originalControl.getComments();
    	for (ControlPollutant pollutant : originalControl.getPollutants()) {
    		this.pollutants.add(new ControlPollutant(this, pollutant));
    	}
    	this.controlMeasureCode = originalControl.getControlMeasureCode();
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

    public List<ControlAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ControlAssignment> assignments) {

        this.assignments.clear();
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
    }

    public List<ControlPollutant> getPollutants() {
        return pollutants;
    }

    public void setPollutants(List<ControlPollutant> pollutants) {

        this.pollutants.clear();
        if (pollutants != null) {
            this.pollutants.addAll(pollutants);
        }
    }

    public ControlMeasureCode getControlMeasureCode() {
        return controlMeasureCode;
    }

    public void setControlMeasureCode(ControlMeasureCode controlMeasureCode) {
        this.controlMeasureCode = controlMeasureCode;
    }
    
    public String getUpgradedDescription() {
		return upgradedDescription;
	}

	public void setUpgradedDescription(String upgradedDescription) {
		this.upgradedDescription = upgradedDescription;
	}

	public Short getNumberOperatingMonths() {
		return numberOperatingMonths;
	}

	public void setNumberOperatingMonths(Short numberOperatingMonths) {
		this.numberOperatingMonths = numberOperatingMonths;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getUpgradedDate() {
		return upgradedDate;
	}

	public void setUpgradedDate(LocalDate upgradedDate) {
		this.upgradedDate = upgradedDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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
