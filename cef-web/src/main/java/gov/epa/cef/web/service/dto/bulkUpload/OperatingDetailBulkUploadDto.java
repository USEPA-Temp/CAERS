package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class OperatingDetailBulkUploadDto extends BaseWorksheetDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Operating Detail ID is required.")
    private Long id;

    @NotNull(message = "Reporting Period ID is required.")
    private Long reportingPeriodId;

    private Short actualHoursPerPeriod;

    @Digits(integer = 2, fraction = 1,
        message = "Percent Apportionment is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double averageHoursPerDay;

    @Digits(integer = 1, fraction = 1,
        message = "Percent Apportionment is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double averageDaysPerWeek;

    private Short averageWeeksPerPeriod;

    @Digits(integer = 3, fraction = 1,
        message = "Percent Winter is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double percentWinter;

    @Digits(integer = 3, fraction = 1,
        message = "Percent Spring is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double percentSpring;

    @Digits(integer = 3, fraction = 1,
        message = "Percent Summer is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double percentSummer;

    @Digits(integer = 3, fraction = 1,
        message = "Percent Fall is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double percentFall;

    public OperatingDetailBulkUploadDto() {

        super(WorksheetName.OperatingDetail);
    }

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
