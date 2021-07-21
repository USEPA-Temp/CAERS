package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OperatingDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long reportingPeriodId;
    private Short actualHoursPerPeriod;
    private BigDecimal avgHoursPerDay;
    private BigDecimal avgDaysPerWeek;
    private Short avgWeeksPerPeriod;
    private BigDecimal percentWinter;
    private BigDecimal percentSpring;
    private BigDecimal percentSummer;
    private BigDecimal percentFall;

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

    public BigDecimal getAvgHoursPerDay() {
        return avgHoursPerDay;
    }

    public void setAvgHoursPerDay(BigDecimal avgHoursPerDay) {
        this.avgHoursPerDay = avgHoursPerDay;
    }

    public BigDecimal getAvgDaysPerWeek() {
        return avgDaysPerWeek;
    }

    public void setAvgDaysPerWeek(BigDecimal avgDaysPerWeek) {
        this.avgDaysPerWeek = avgDaysPerWeek;
    }

    public Short getAvgWeeksPerPeriod() {
        return avgWeeksPerPeriod;
    }

    public void setAvgWeeksPerPeriod(Short avgWeeksPerPeriod) {
        this.avgWeeksPerPeriod = avgWeeksPerPeriod;
    }

    public BigDecimal getPercentWinter() {
        return percentWinter;
    }

    public void setPercentWinter(BigDecimal percentWinter) {
        this.percentWinter = percentWinter;
    }

    public BigDecimal getPercentSpring() {
        return percentSpring;
    }

    public void setPercentSpring(BigDecimal percentSpring) {
        this.percentSpring = percentSpring;
    }

    public BigDecimal getPercentSummer() {
        return percentSummer;
    }

    public void setPercentSummer(BigDecimal percentSummer) {
        this.percentSummer = percentSummer;
    }

    public BigDecimal getPercentFall() {
        return percentFall;
    }

    public void setPercentFall(BigDecimal percentFall) {
        this.percentFall = percentFall;
    }

    public OperatingDetailDto withId(Long id) {

        setId(id);
        return this;
    }
}
