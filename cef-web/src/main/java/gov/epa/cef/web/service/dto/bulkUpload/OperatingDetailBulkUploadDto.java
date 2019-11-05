package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class OperatingDetailBulkUploadDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long reportingPeriodId;
    private Short actualHoursPerPeriod;
    private Double averageHoursPerDay;
    private Double averageDaysPerWeek;
    private Short averageWeeksPerPeriod;
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

    public Double getAverageHoursPerDay() {
        return averageHoursPerDay;
    }

    public void setAverageHoursPerDay(Double averageHoursPerDay) {
        this.averageHoursPerDay = averageHoursPerDay;
    }

    public Double getAverageDaysPerWeek() {
        return averageDaysPerWeek;
    }

    public void setAverageDaysPerWeek(Double averageDaysPerWeek) {
        this.averageDaysPerWeek = averageDaysPerWeek;
    }

    public Short getAverageWeeksPerPeriod() {
        return averageWeeksPerPeriod;
    }

    public void setAverageWeeksPerPeriod(Short averageWeeksPerPeriod) {
        this.averageWeeksPerPeriod = averageWeeksPerPeriod;
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
