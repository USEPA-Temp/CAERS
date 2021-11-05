/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import gov.epa.cef.web.annotation.CsvColumn;
import gov.epa.cef.web.annotation.CsvFileName;

import java.io.Serializable;

@CsvFileName(name = "operating_details.csv")
public class OperatingDetailBulkUploadDto extends BaseWorksheetDto implements Serializable{

    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Operating Detail ID is required.")
    private Long id;

    @NotNull(message = "Reporting Period ID is required.")
    private Long reportingPeriodId;

    @NotNull(message = "Actual Hours Per Period is required.")
    @Pattern(regexp = "^\\d{0,4}$",
        message = "Actual Hours Per Period is not in expected numeric format; found '${validatedValue}'.")
    private String actualHoursPerPeriod;

    @NotNull(message = "Average Hours Per Day is required.")
    @Pattern(regexp = "^\\d{0,2}(\\.\\d)?$",
        message = "Average Hours Per Day is not in expected numeric format: '{2}.{1}' digits; found '${validatedValue}'.")
    private String averageHoursPerDay;

    @NotNull(message = "Average Days Per Week is required.")
    @Pattern(regexp = "^\\d?(\\.\\d)?$",
        message = "Average Days Per Week is not in expected numeric format: '{1}.{1}' digits; found '${validatedValue}'.")
    private String averageDaysPerWeek;

    @NotNull(message = "Average Weeks Per Period is required.")
    @Pattern(regexp = "^\\d{0,2}$",
        message = "Average Weeks Per Period is not in expected numeric format; found '${validatedValue}'.")
    private String averageWeeksPerPeriod;

    @NotBlank(message = "Winter Operating Percent is required.")
    @Pattern(regexp = PercentPattern,
        message = "Winter Operating Percent is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentWinter;

    @NotBlank(message = "Spring Operating Percent is required.")
    @Pattern(regexp = PercentPattern,
        message = "Spring Operating Percent is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentSpring;

    @NotBlank(message = "Summer Operating Percent is required.")
    @Pattern(regexp = PercentPattern,
        message = "Summer Operating Percent is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentSummer;

    @NotBlank(message = "Fall Operating Percent is required.")
    @Pattern(regexp = PercentPattern,
        message = "Fall Operating Percent is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentFall;

    public OperatingDetailBulkUploadDto() {

        super(WorksheetName.OperatingDetail);
    }

    @CsvColumn(name = "ID", order = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @CsvColumn(name = "Reporting Period ID", order = 2)
    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }

    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    @CsvColumn(name = "Actual Hours Per Period", order = 3)
    public String getActualHoursPerPeriod() {
        return actualHoursPerPeriod;
    }

    public void setActualHoursPerPeriod(String actualHoursPerPeriod) {
        this.actualHoursPerPeriod = actualHoursPerPeriod;
    }

    @CsvColumn(name = "Average Hours Per Day", order = 4)
    public String getAverageHoursPerDay() {
        return averageHoursPerDay;
    }

    public void setAverageHoursPerDay(String averageHoursPerDay) {
        this.averageHoursPerDay = averageHoursPerDay;
    }

    @CsvColumn(name = "Average Days Per Week", order = 5)
    public String getAverageDaysPerWeek() {
        return averageDaysPerWeek;
    }

    public void setAverageDaysPerWeek(String averageDaysPerWeek) {
        this.averageDaysPerWeek = averageDaysPerWeek;
    }

    @CsvColumn(name = "Average Weeks Per Period", order = 6)
    public String getAverageWeeksPerPeriod() {
        return averageWeeksPerPeriod;
    }

    public void setAverageWeeksPerPeriod(String averageWeeksPerPeriod) {
        this.averageWeeksPerPeriod = averageWeeksPerPeriod;
    }

    @CsvColumn(name = "Percent Winter", order = 7)
    public String getPercentWinter() {
        return percentWinter;
    }

    public void setPercentWinter(String percentWinter) {
        this.percentWinter = percentWinter;
    }

    @CsvColumn(name = "Percent Spring", order = 8)
    public String getPercentSpring() {
        return percentSpring;
    }

    public void setPercentSpring(String percentSpring) {
        this.percentSpring = percentSpring;
    }

    @CsvColumn(name = "Percent Summer", order = 9)
    public String getPercentSummer() {
        return percentSummer;
    }

    public void setPercentSummer(String percentSummer) {
        this.percentSummer = percentSummer;
    }

    @CsvColumn(name = "Percent Fall", order = 10)
    public String getPercentFall() {
        return percentFall;
    }

    public void setPercentFall(String percentFall) {
        this.percentFall = percentFall;
    }

}
