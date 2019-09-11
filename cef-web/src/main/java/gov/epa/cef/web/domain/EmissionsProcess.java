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

/**
 * EmissionsProcess entity
 */
@Entity
@Table(name = "emissions_process")
public class EmissionsProcess extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_unit_id", nullable = false)
    private EmissionsUnit emissionsUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_engine_type_code")
    private AircraftEngineTypeCode aircraftEngineTypeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)
    private OperatingStatusCode operatingStatusCode;

    @Column(name = "emissions_process_identifier", nullable = false, length = 20)
    private String emissionsProcessIdentifier;

    @Column(name = "status_year")
    private Short statusYear;

    @Column(name = "scc_code", nullable = false, length = 20)
    private String sccCode;

    @Column(name = "scc_description", length = 500)
    private String sccDescription;

    @Column(name = "scc_short_name", length = 100)
    private String sccShortName;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "comments", length = 200)
    private String comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsProcess")
    private Set<ReleasePointAppt> releasePointAppts = new HashSet<ReleasePointAppt>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsProcess")
    private Set<ReportingPeriod> reportingPeriods = new HashSet<ReportingPeriod>(0);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsProcess")
    private Set<ControlAssignment> controlAssignments = new HashSet<ControlAssignment>(0);
    
    /***
     * Default constructor
     */
    public EmissionsProcess() {}
    
    
    /***
     * Copy constructor
     * @param unit The emissions unit object that this process should be associated with
     * @param originalProcess The process object being copied
     */
    public EmissionsProcess(EmissionsUnit unit, EmissionsProcess originalProcess) {
		this.id = originalProcess.getId();
        this.emissionsUnit = unit;
        this.aircraftEngineTypeCode = originalProcess.getAircraftEngineTypeCode();
        this.operatingStatusCode = originalProcess.getOperatingStatusCode();
        this.emissionsProcessIdentifier = originalProcess.getEmissionsProcessIdentifier();
        this.statusYear = originalProcess.getStatusYear();
        this.sccCode = originalProcess.getSccCode();
        this.sccShortName = originalProcess.getSccShortName();
        this.description = originalProcess.getDescription();
        this.comments = originalProcess.getComments();

        for (ReleasePointAppt originalApportionment : originalProcess.getReleasePointAppts()) {
        	ReleasePoint rp = null;
        	for(ReleasePoint newReleasePoint : this.emissionsUnit.getFacilitySite().getReleasePoints()) {
        		if (newReleasePoint.getId().equals(originalApportionment.getReleasePoint().getId())) {
        			rp = newReleasePoint;
        			break;
        		}
        	}
        	this.releasePointAppts.add(new ReleasePointAppt(rp, this, originalApportionment));
        }

        for (ReportingPeriod reportingPeriod : originalProcess.getReportingPeriods()) {
        	this.reportingPeriods.add(new ReportingPeriod(this, reportingPeriod));
        }        
    }
    
    public EmissionsUnit getEmissionsUnit() {
        return this.emissionsUnit;
    }
    public void setEmissionsUnit(EmissionsUnit emissionsUnit) {
        this.emissionsUnit = emissionsUnit;
    }

    public AircraftEngineTypeCode getAircraftEngineTypeCode() {
        return aircraftEngineTypeCode;
    }

    public void setAircraftEngineTypeCode(AircraftEngineTypeCode aircraftEngineTypeCode) {
        this.aircraftEngineTypeCode = aircraftEngineTypeCode;
    }

    public OperatingStatusCode getOperatingStatusCode() {
        return this.operatingStatusCode;
    }
    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getEmissionsProcessIdentifier() {
        return this.emissionsProcessIdentifier;
    }
    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    public Short getStatusYear() {
        return this.statusYear;
    }
    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public String getSccCode() {
        return this.sccCode;
    }
    public void setSccCode(String sccCode) {
        this.sccCode = sccCode;
    }

    public String getSccDescription() {
        return sccDescription;
    }

    public void setSccDescription(String sccDescription) {
        this.sccDescription = sccDescription;
    }

    public String getSccShortName() {
        return this.sccShortName;
    }
    public void setSccShortName(String sccShortName) {
        this.sccShortName = sccShortName;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<ReleasePointAppt> getReleasePointAppts() {
        return this.releasePointAppts;
    }
    public void setReleasePointAppts(Set<ReleasePointAppt> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
    }

    public Set<ReportingPeriod> getReportingPeriods() {
        return this.reportingPeriods;
    }
    public void setReportingPeriods(Set<ReportingPeriod> reportingPeriods) {
        this.reportingPeriods = reportingPeriods;
    }
    
    
    public Set<ControlAssignment> getControlAssignments() {
        return controlAssignments;
    }


    public void setControlAssignments(Set<ControlAssignment> controlAssignments) {
        this.controlAssignments = controlAssignments;
    }


    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;

		for (ReleasePointAppt releasePointAppt : this.releasePointAppts) {
			releasePointAppt.clearId();
		}
		for (ReportingPeriod reportingPeriod : this.reportingPeriods) {
			reportingPeriod.clearId();
		}
    }
}