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
import java.io.Serializable;

public class EmissionsProcessBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Emissions Process ID is required.")
    private Long id;

    @NotNull(message = "Emissions Unit ID is required.")
    private Long emissionsUnitId;

    @NotBlank(message = "Emissions Process Identifier is required.")
    @Size(max = 20, message = "Emissions Process Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsProcessIdentifier;

    private String displayName;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCode;

    @NotBlank(message = "Operating Status Year is required.")
    @Pattern(regexp = YearPattern,
        message = "Operating Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    @NotBlank(message = "SCC Code is required.")
    @Size(max = 20, message = "SCC Code can not exceed {max} chars; found '${validatedValue}'.")
    private String sccCode;

    @Size(max = 100, message = "SCC Short Name can not exceed {max} chars; found '${validatedValue}'.")
    private String sccShortName;

    @NotBlank(message = "Description is required.")
    @Size(max = 200, message = "Description can not exceed {max} chars; found '${validatedValue}'.")
    private String description;

    @Size(max = 10, message = "Aircraft Engine Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String aircraftEngineTypeCode;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @Size(max = 500, message = "SCC Description can not exceed {max} chars; found '${validatedValue}'.")
    private String sccDescription;

    public EmissionsProcessBulkUploadDto() {

        super(WorksheetName.EmissionsProcess);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmissionsUnitId() {
        return emissionsUnitId;
    }
    public void setEmissionsUnitId(Long emissionsUnitId) {
        this.emissionsUnitId = emissionsUnitId;
    }

    public String getAircraftEngineTypeCode() {
        return aircraftEngineTypeCode;
    }
    public void setAircraftEngineTypeCode(String aircraftEngineTypeCode) {
        this.aircraftEngineTypeCode = aircraftEngineTypeCode;
    }

    public String getOperatingStatusCode() {
        return operatingStatusCode;
    }
    public void setOperatingStatusCode(String operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getEmissionsProcessIdentifier() {
        return emissionsProcessIdentifier;
    }
    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatusYear() {
        return statusYear;
    }
    public void setStatusYear(String statusYear) {
        this.statusYear = statusYear;
    }

    public String getSccCode() {
        return sccCode;
    }
    public void setSccCode(String sccCode) {
        this.sccCode = sccCode;
    }

    public String getSccDescription() {
        return sccDescription;
    }
    public void setSccDescription(String sccDescription) {
        this.sccDescription = sccDescription;
    }

    public String getSccShortName() {
        return sccShortName;
    }
    public void setSccShortName(String sccShortName) {
        this.sccShortName = sccShortName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}

