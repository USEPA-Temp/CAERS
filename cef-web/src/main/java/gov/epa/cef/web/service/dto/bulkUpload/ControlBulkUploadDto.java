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
import javax.validation.constraints.Size;

import gov.epa.cef.web.annotation.CsvColumn;
import gov.epa.cef.web.annotation.CsvFileName;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.DecimalMax;
import java.io.Serializable;

@CsvFileName(name = "controls.csv")
public class ControlBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @NotBlank(message = "Control Measure Code is required.")
    @Size(max = 20, message = "Control Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String controlMeasureCode;
    
    private String controlMeasureCodeDescription;

    @Size(max = 200, message = "Description can not exceed {max} chars.")
    private String description;

    @NotNull(message = "Facility Site ID is required.")
    private Long facilitySiteId;

    @NotNull(message = "Control ID is required")
    private Long id;

    @NotBlank(message = "Control Identifier is required.")
    @Size(max = 20, message = "Control Identifier can not exceed {max} chars.")
    private String identifier;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars.")
    private String operatingStatusCode;
    
    private String operatingStatusCodeDescription;
    
    @Pattern(regexp = YearPattern,
        message = "Operating Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    // field has become legacy with CEF-984. Data is no longer saved in db.
    // field remains to prevent error when uploading older version of template.
    @Pattern(regexp = PercentPattern,
        message = "Percent Capture is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentCapture;

	@Pattern(regexp = "^\\d{0,3}(\\.\\d)?$",
        message = "Percent Control Effectiveness is not in expected numeric format: '{3}.{1}' digits; found '${validatedValue}'.")
    private String percentControl;
    
    @DecimalMin(value = "1", message = "Control Number of Operating Months must be an integer from 1-12.")
    @DecimalMax(value = "12", message = "Control Number of Operating Months must be an integer from 1-12.")
    private String numberOperatingMonths;
    
    private String startDate;
    
    private String upgradeDate;
    
    private String endDate;
    
    @Size(max = 200, message = "Description can not exceed {max} chars.")
    private String upgradeDescription;

    public ControlBulkUploadDto() {

        super(WorksheetName.Control);
    }

    @CsvColumn(name = "Comments", order = 14)
    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

    @CsvColumn(name = "Control Measure Code", order = 8)
    public String getControlMeasureCode() {

        return controlMeasureCode;
    }

    public void setControlMeasureCode(String controlMeasureCode) {

        this.controlMeasureCode = controlMeasureCode;
    }

    @CsvColumn(name = "Control Measure Description", order = 8)
    public String getControlMeasureCodeDescription() {
        return controlMeasureCodeDescription;
    }
    public void setControlMeasureCodeDescription(String controlMeasureCodeDescription) {
        this.controlMeasureCodeDescription = controlMeasureCodeDescription;
    }

    @CsvColumn(name = "Description", order = 4)
    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    @CsvColumn(name = "Facility Site ID", order = 1)
    public Long getFacilitySiteId() {

        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {

        this.facilitySiteId = facilitySiteId;
    }

    @CsvColumn(name = "ID", order = 3)
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @CsvColumn(name = "Identifier", order = 2)
    public String getIdentifier() {

        return identifier;
    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    @CsvColumn(name = "Operating Status Code", order = 6)
    public String getOperatingStatusCode() {

        return operatingStatusCode;
    }

    public void setOperatingStatusCode(String operatingStatusCode) {

        this.operatingStatusCode = operatingStatusCode;
    }

    @CsvColumn(name = "Operating Status Description", order = 6)
    public String getOperatingStatusCodeDescription() {
        return operatingStatusCodeDescription;
    }
    public void setOperatingStatusCodeDescription(String operatingStatusCodeDescription) {
        this.operatingStatusCodeDescription = operatingStatusCodeDescription;
    }

    public String getPercentCapture() {

        return percentCapture;
    }

    public void setPercentCapture(String percentCapture) {

        this.percentCapture = percentCapture;
    }

    @CsvColumn(name = "Percent Control", order = 5)
    public String getPercentControl() {

        return percentControl;
    }

    public void setPercentControl(String percentControl) {

        this.percentControl = percentControl;
    }

    @CsvColumn(name = "Number Operating Months", order = 9)
	public String getNumberOperatingMonths() {
		return numberOperatingMonths;
	}

	public void setNumberOperatingMonths(String numberOperatingMonths) {
		this.numberOperatingMonths = numberOperatingMonths;
	}

    @CsvColumn(name = "Upgrade Description", order = 13)
	public String getUpgradeDescription() {
		return upgradeDescription;
	}

	public void setUpgradeDescription(String upgradeDescription) {
		this.upgradeDescription = upgradeDescription;
	}

    @CsvColumn(name = "Start Date", order = 10)
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

    @CsvColumn(name = "Upgrade Date", order = 11)
	public String getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(String upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

    @CsvColumn(name = "End Date", order = 12)
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

    @CsvColumn(name = "Status Year", order = 7)
	public String getStatusYear() {
		return statusYear;
	}

	public void setStatusYear(String statusYear) {
		this.statusYear = statusYear;
	}

}
