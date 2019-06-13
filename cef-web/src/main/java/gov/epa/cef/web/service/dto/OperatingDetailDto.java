package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class OperatingDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long reportingPeriodId;
    private Short actualHoursPerPeriod;
    private Double avgHoursPerDay;
    private Double avgDaysPerWeek;
    private Byte avgWeeksPerPeriod;
    private Double percentWinter;
    private Double percentSpring;
    private Double percentSummer;
    private Double percentFall;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }

    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    public Short getActualHoursPerPeriod() {
        return actualHoursPerPeriod;
    }

    public void setActualHoursPerPeriod(Short actualHoursPerPeriod) {
        this.actualHoursPerPeriod = actualHoursPerPeriod;
    }

    public Double getAvgHoursPerDay() {
        return avgHoursPerDay;
    }

    public void setAvgHoursPerDay(Double avgHoursPerDay) {
        this.avgHoursPerDay = avgHoursPerDay;
    }

    public Double getAvgDaysPerWeek() {
        return avgDaysPerWeek;
    }

    public void setAvgDaysPerWeek(Double avgDaysPerWeek) {
        this.avgDaysPerWeek = avgDaysPerWeek;
    }

    public Byte getAvgWeeksPerPeriod() {
        return avgWeeksPerPeriod;
    }

    public void setAvgWeeksPerPeriod(Byte avgWeeksPerPeriod) {
        this.avgWeeksPerPeriod = avgWeeksPerPeriod;
    }

    public Double getPercentWinter() {
        return percentWinter;
    }

    public void setPercentWinter(Double percentWinter) {
        this.percentWinter = percentWinter;
    }

    public Double getPercentSpring() {
        return percentSpring;
    }

    public void setPercentSpring(Double percentSpring) {
        this.percentSpring = percentSpring;
    }

    public Double getPercentSummer() {
        return percentSummer;
    }

    public void setPercentSummer(Double percentSummer) {
        this.percentSummer = percentSummer;
    }

    public Double getPercentFall() {
        return percentFall;
    }

    public void setPercentFall(Double percentFall) {
        this.percentFall = percentFall;
    }

}
