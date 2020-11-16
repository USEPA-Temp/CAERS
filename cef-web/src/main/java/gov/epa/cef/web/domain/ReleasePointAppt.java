package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * ReleasePointAppt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "release_point_appt")

public class ReleasePointAppt extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_point_id", nullable = false)
    private ReleasePoint releasePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_process_id", nullable = false)
    private EmissionsProcess emissionsProcess;
    
    @Column(name = "percent", nullable = false, precision = 5, scale = 2)
    private Double percent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_path_id")
    private ControlPath controlPath;

    
    /***
     * Default constructor
     */
    public ReleasePointAppt() {}

    
    /***
     * Copy constructor
     * @param newReleasePoint The release point that should be associated with this release point apportionment object
     * @param newEmissionProcess The process that should be associated with this release point apportionment object
     * @param newControlPath The control path that should be associated with this release point apportionment
     * @param originalReleasePointAppt The release point apportionment object that is being copied
     */
    public ReleasePointAppt(ReleasePoint newReleasePoint, EmissionsProcess newEmissionProcess, ControlPath newControlPath, ReleasePointAppt originalReleasePointAppt) {
		this.id = originalReleasePointAppt.getId();
    	this.releasePoint = newReleasePoint;
    	this.emissionsProcess = newEmissionProcess;
    	this.percent = originalReleasePointAppt.getPercent();
    	this.controlPath = newControlPath;
    }
    
    
    public ReleasePoint getReleasePoint() {
        return this.releasePoint;
    }

    public void setReleasePoint(ReleasePoint releasePoint) {
        this.releasePoint = releasePoint;
    }

    public EmissionsProcess getEmissionsProcess() {
        return this.emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcess emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    public Double getPercent() {
        return this.percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public ControlPath getControlPath() {
        return controlPath;
    }

    public void setControlPath(ControlPath controlPath) {
        this.controlPath = controlPath;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}