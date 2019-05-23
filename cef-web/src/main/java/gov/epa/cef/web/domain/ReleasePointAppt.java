package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ReleasePointAppt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "release_point_appt")

public class ReleasePointAppt implements java.io.Serializable {

    // Fields

    private Long id;
    private ReleasePoint releasePoint;
    private EmissionsProcess emissionsProcess;
    private Double percent;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;

    // Constructors

    /** default constructor */
    public ReleasePointAppt() {
    }

    /** full constructor */
    public ReleasePointAppt(Long id, ReleasePoint releasePoint, EmissionsProcess emissionsProcess, Double percent,
            String createdBy, Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.releasePoint = releasePoint;
        this.emissionsProcess = emissionsProcess;
        this.percent = percent;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    // Property accessors
    @Id

    @Column(name = "id", unique = true, nullable = false)

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_point_id", nullable = false)

    public ReleasePoint getReleasePoint() {
        return this.releasePoint;
    }

    public void setReleasePoint(ReleasePoint releasePoint) {
        this.releasePoint = releasePoint;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emissions_process_id", nullable = false)

    public EmissionsProcess getEmissionsProcess() {
        return this.emissionsProcess;
    }

    public void setEmissionsProcess(EmissionsProcess emissionsProcess) {
        this.emissionsProcess = emissionsProcess;
    }

    @Column(name = "percent", nullable = false, precision = 4, scale = 1)

    public Double getPercent() {
        return this.percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    @Column(name = "created_by", nullable = false)

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_date", nullable = false, length = 29)

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_modified_by", nullable = false)

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "last_modified_date", nullable = false, length = 29)

    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}