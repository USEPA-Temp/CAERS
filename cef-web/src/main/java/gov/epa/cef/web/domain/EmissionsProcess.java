package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EmissionsProcess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emissions_process")

public class EmissionsProcess implements java.io.Serializable {

    // Fields

    private Long id;
    private EmissionsUnit emissionsUnit;
    private OperatingStatusCode operatingStatusCode;
    private String emissionsProcessIdentifier;
    private Short statusYear;
    private String sccCode;
    private String sccShortName;
    private String description;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private Set<ReleasePointAppt> releasePointAppts = new HashSet<ReleasePointAppt>(0);
    private Set<ReportingPeriod> reportingPeriods = new HashSet<ReportingPeriod>(0);

    // Constructors

    /** default constructor */
    public EmissionsProcess() {
    }

    /** minimal constructor */
    public EmissionsProcess(Long id, EmissionsUnit emissionsUnit, OperatingStatusCode operatingStatusCode,
            String emissionsProcessIdentifier, Short statusYear, String sccCode, String createdBy,
            Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.emissionsUnit = emissionsUnit;
        this.operatingStatusCode = operatingStatusCode;
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
        this.statusYear = statusYear;
        this.sccCode = sccCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public EmissionsProcess(Long id, EmissionsUnit emissionsUnit, OperatingStatusCode operatingStatusCode,
            String emissionsProcessIdentifier, Short statusYear, String sccCode, String sccShortName,
            String description, String createdBy, Timestamp createdDate, String lastModifiedBy,
            Timestamp lastModifiedDate, Set<ReleasePointAppt> releasePointAppts,
            Set<ReportingPeriod> reportingPeriods) {
        this.id = id;
        this.emissionsUnit = emissionsUnit;
        this.operatingStatusCode = operatingStatusCode;
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
        this.statusYear = statusYear;
        this.sccCode = sccCode;
        this.sccShortName = sccShortName;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.releasePointAppts = releasePointAppts;
        this.reportingPeriods = reportingPeriods;
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
    @JoinColumn(name = "emissions_unit_id", nullable = false)

    public EmissionsUnit getEmissionsUnit() {
        return this.emissionsUnit;
    }

    public void setEmissionsUnit(EmissionsUnit emissionsUnit) {
        this.emissionsUnit = emissionsUnit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)

    public OperatingStatusCode getOperatingStatusCode() {
        return this.operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    @Column(name = "emissions_process_identifier", nullable = false, length = 20)

    public String getEmissionsProcessIdentifier() {
        return this.emissionsProcessIdentifier;
    }

    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    @Column(name = "status_year", nullable = false)

    public Short getStatusYear() {
        return this.statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    @Column(name = "scc_code", nullable = false, length = 20)

    public String getSccCode() {
        return this.sccCode;
    }

    public void setSccCode(String sccCode) {
        this.sccCode = sccCode;
    }

    @Column(name = "scc_short_name", length = 100)

    public String getSccShortName() {
        return this.sccShortName;
    }

    public void setSccShortName(String sccShortName) {
        this.sccShortName = sccShortName;
    }

    @Column(name = "description", length = 200)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsProcess")

    public Set<ReleasePointAppt> getReleasePointAppts() {
        return this.releasePointAppts;
    }

    public void setReleasePointAppts(Set<ReleasePointAppt> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emissionsProcess")

    public Set<ReportingPeriod> getReportingPeriods() {
        return this.reportingPeriods;
    }

    public void setReportingPeriods(Set<ReportingPeriod> reportingPeriods) {
        this.reportingPeriods = reportingPeriods;
    }

}