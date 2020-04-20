package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = PositiveShortPattern,
        message = "Actual Hours Per Period is not in expected numeric format; found '${validatedValue}'.")
    private String actualHoursPerPeriod;

    @Pattern(regexp = "^\\d{0,2}(\\.\\d)?$",
        message = "Average Hours Per Day is not in expected numeric format: '{2}.{1}' digits; found '${validatedValue}'.")
    private String averageHoursPerDay;

    @Pattern(regexp = "^\\d*(\\.\\d)?$",
        message = "Average Days Per Week is not in expected numeric format: '{1}.{1}' digits; found '${validatedValue}'.")
    private String averageDaysPerWeek;

    @Pattern(regexp = PositiveShortPattern,
        message = "Average Weeks Per Period is not in expected numeric format; found '${validatedValue}'.")
    private String averageWeeksPerPeriod;

    @Pattern(regexp = PercentPattern,
        message = "Percent Winter is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentWinter;

    @Pattern(regexp = PercentPattern,
        message = "Percent Spring is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentSpring;

    @Pattern(regexp = PercentPattern,
        message = "Percent Summer is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentSummer;

    @Pattern(regexp = PercentPattern,
        message = "Percent Fall is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentFall;

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

    public String getActualHoursPerPeriod() {
        return actualHoursPerPeriod;
    }

    public void setActualHoursPerPeriod(String actualHoursPerPeriod) {
        this.actualHoursPerPeriod = actualHoursPerPeriod;
    }

    public String getAverageHoursPerDay() {
        return averageHoursPerDay;
    }

    public void setAverageHoursPerDay(String averageHoursPerDay) {
        this.averageHoursPerDay = averageHoursPerDay;
    }

    public String getAverageDaysPerWeek() {
        return averageDaysPerWeek;
    }

    public void setAverageDaysPerWeek(String averageDaysPerWeek) {
        this.averageDaysPerWeek = averageDaysPerWeek;
    }

    public String getAverageWeeksPerPeriod() {
        return averageWeeksPerPeriod;
    }

    public void setAverageWeeksPerPeriod(String averageWeeksPerPeriod) {
        this.averageWeeksPerPeriod = averageWeeksPerPeriod;
    }

    public String getPercentWinter() {
        return percentWinter;
    }

    public void setPercentWinter(String percentWinter) {
        this.percentWinter = percentWinter;
    }

    public String getPercentSpring() {
        return percentSpring;
    }

    public void setPercentSpring(String percentSpring) {
        this.percentSpring = percentSpring;
    }

    public String getPercentSummer() {
        return percentSummer;
    }

    public void setPercentSummer(String percentSummer) {
        this.percentSummer = percentSummer;
    }

    public String getPercentFall() {
        return percentFall;
    }

    public void setPercentFall(String percentFall) {
        this.percentFall = percentFall;
    }

}
