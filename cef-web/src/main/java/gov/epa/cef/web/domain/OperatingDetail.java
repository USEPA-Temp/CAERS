package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * OperatingDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operating_detail")

public class OperatingDetail extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_period_id", nullable = false)
    private ReportingPeriod reportingPeriod;
    
    @Column(name = "actual_hours_per_period", precision = 4, scale = 0)
    private Short actualHoursPerPeriod;
    
    @Column(name = "avg_hours_per_day", precision = 3, scale = 1)
    private Double avgHoursPerDay;
    
    @Column(name = "avg_days_per_week", precision = 2, scale = 1)
    private Double avgDaysPerWeek;
    
    @Column(name = "avg_weeks_per_period", precision = 2, scale = 0)
    private Short avgWeeksPerPeriod;
    
    @Column(name = "percent_winter", precision = 4, scale = 1)    
    private Double percentWinter;
    
    @Column(name = "percent_spring", precision = 4, scale = 1)
    private Double percentSpring;
    
    @Column(name = "percent_summer", precision = 4, scale = 1)
    private Double percentSummer;
    
    @Column(name = "percent_fall", precision = 4, scale = 1)
    private Double percentFall;

    // Constructors

    /** default constructor */
    public OperatingDetail() {
    }

    public ReportingPeriod getReportingPeriod() {
        return this.reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriod reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public Short getActualHoursPerPeriod() {
        return this.actualHoursPerPeriod;
    }

    public void setAvgHoursPerPeriod(Short avgHoursPerPeriod) {
        this.actualHoursPerPeriod = avgHoursPerPeriod;
    }

    public Double getAvgHoursPerDay() {
        return this.avgHoursPerDay;
    }

    public void setAvgHoursPerDay(Double avgHoursPerDay) {
        this.avgHoursPerDay = avgHoursPerDay;
    }

    public Double getAvgDaysPerWeek() {
        return this.avgDaysPerWeek;
    }

    public void setAvgDaysPerWeek(Double avgDaysPerWeek) {
        this.avgDaysPerWeek = avgDaysPerWeek;
    }

    public Short getAvgWeeksPerPeriod() {
        return this.avgWeeksPerPeriod;
    }

    public void setAvgWeeksPerPeriod(Short avgWeeksPerPeriod) {
        this.avgWeeksPerPeriod = avgWeeksPerPeriod;
    }

    public Double getPercentWinter() {
        return this.percentWinter;
    }

    public void setPercentWinter(Double percentWinter) {
        this.percentWinter = percentWinter;
    }

    public Double getPercentSpring() {
        return this.percentSpring;
    }

    public void setPercentSpring(Double percentSpring) {
        this.percentSpring = percentSpring;
    }

    public Double getPercentSummer() {
        return this.percentSummer;
    }

    public void setPercentSummer(Double percentSummer) {
        this.percentSummer = percentSummer;
    }

    public Double getPercentFall() {
        return this.percentFall;
    }

    public void setPercentFall(Double percentFall) {
        this.percentFall = percentFall;
    }

}