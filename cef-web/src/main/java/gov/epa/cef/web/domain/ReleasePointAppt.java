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
    
    @Column(name = "percent", nullable = false, precision = 4, scale = 1)
    private Double percent;

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

}