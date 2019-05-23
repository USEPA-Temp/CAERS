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
 * OperatingDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operating_detail")

public class OperatingDetail implements java.io.Serializable {

    // Fields

    private Long id;
    private ReportingPeriod reportingPeriod;
    private Short avgHoursPerPeriod;
    private Double avgHoursPerDay;
    private Double avgDaysPerWeek;
    private Byte avgWeeksPerPeriod;
    private Double percentWinter;
    private Double percentSpring;
    private Double percentSummer;
    private Double percentFall;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;

    // Constructors

    /** default constructor */
    public OperatingDetail() {
    }

    /** minimal constructor */
    public OperatingDetail(Long id, ReportingPeriod reportingPeriod, String createdBy, Timestamp createdDate,
            String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.reportingPeriod = reportingPeriod;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public OperatingDetail(Long id, ReportingPeriod reportingPeriod, Short avgHoursPerPeriod, Double avgHoursPerDay,
            Double avgDaysPerWeek, Byte avgWeeksPerPeriod, Double percentWinter, Double percentSpring,
            Double percentSummer, Double percentFall, String createdBy, Timestamp createdDate, String lastModifiedBy,
            Timestamp lastModifiedDate) {
        this.id = id;
        this.reportingPeriod = reportingPeriod;
        this.avgHoursPerPeriod = avgHoursPerPeriod;
        this.avgHoursPerDay = avgHoursPerDay;
        this.avgDaysPerWeek = avgDaysPerWeek;
        this.avgWeeksPerPeriod = avgWeeksPerPeriod;
        this.percentWinter = percentWinter;
        this.percentSpring = percentSpring;
        this.percentSummer = percentSummer;
        this.percentFall = percentFall;
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
    @JoinColumn(name = "reporting_period_id", nullable = false)

    public ReportingPeriod getReportingPeriod() {
        return this.reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    @Column(name = "avg_hours_per_period", precision = 4, scale = 0)

    public Short getAvgHoursPerPeriod() {
        return this.avgHoursPerPeriod;
    }

    public void setAvgHoursPerPeriod(Short avgHoursPerPeriod) {
        this.avgHoursPerPeriod = avgHoursPerPeriod;
    }

    @Column(name = "avg_hours_per_day", precision = 3, scale = 1)

    public Double getAvgHoursPerDay() {
        return this.avgHoursPerDay;
    }

    public void setAvgHoursPerDay(Double avgHoursPerDay) {
        this.avgHoursPerDay = avgHoursPerDay;
    }

    @Column(name = "avg_days_per_week", precision = 2, scale = 1)

    public Double getAvgDaysPerWeek() {
        return this.avgDaysPerWeek;
    }

    public void setAvgDaysPerWeek(Double avgDaysPerWeek) {
        this.avgDaysPerWeek = avgDaysPerWeek;
    }

    @Column(name = "avg_weeks_per_period", precision = 2, scale = 0)

    public Byte getAvgWeeksPerPeriod() {
        return this.avgWeeksPerPeriod;
    }

    public void setAvgWeeksPerPeriod(Byte avgWeeksPerPeriod) {
        this.avgWeeksPerPeriod = avgWeeksPerPeriod;
    }

    @Column(name = "percent_winter", precision = 4, scale = 1)

    public Double getPercentWinter() {
        return this.percentWinter;
    }

    public void setPercentWinter(Double percentWinter) {
        this.percentWinter = percentWinter;
    }

    @Column(name = "percent_spring", precision = 4, scale = 1)

    public Double getPercentSpring() {
        return this.percentSpring;
    }

    public void setPercentSpring(Double percentSpring) {
        this.percentSpring = percentSpring;
    }

    @Column(name = "percent_summer", precision = 4, scale = 1)

    public Double getPercentSummer() {
        return this.percentSummer;
    }

    public void setPercentSummer(Double percentSummer) {
        this.percentSummer = percentSummer;
    }

    @Column(name = "percent_fall", precision = 4, scale = 1)

    public Double getPercentFall() {
        return this.percentFall;
    }

    public void setPercentFall(Double percentFall) {
        this.percentFall = percentFall;
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