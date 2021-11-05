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

import java.io.Serializable;

@CsvFileName(name = "emissions_processes.csv")
public class EmissionsProcessBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Emissions Process ID is required.")
    private Long id;

    @NotNull(message = "Emissions Unit ID is required.")
    private Long emissionsUnitId;
    
    private String emissionsUnitName;

    @NotBlank(message = "Emissions Process Identifier is required.")
    @Size(max = 20, message = "Emissions Process Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String emissionsProcessIdentifier;

    private String displayName;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCode;
    
    private String operatingStatusCodeDescription;

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
    
    private String aircraftEngineTypeCodeDescription;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @Size(max = 500, message = "SCC Description can not exceed {max} chars; found '${validatedValue}'.")
    private String sccDescription;

    public EmissionsProcessBulkUploadDto() {

        super(WorksheetName.EmissionsProcess);
    }

    @CsvColumn(name = "ID", order = 3)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @CsvColumn(name = "Emissions Unit ID", order = 1)
    public Long getEmissionsUnitId() {
        return emissionsUnitId;
    }
    public void setEmissionsUnitId(Long emissionsUnitId) {
        this.emissionsUnitId = emissionsUnitId;
    }

    @CsvColumn(name = "Emissions Unit Name", order = 1)
    public String getEmissionsUnitName() {
        return emissionsUnitName;
    }
    public void setEmissionsUnitName(String emissionsUnitName) {
        this.emissionsUnitName = emissionsUnitName;
    }

    @CsvColumn(name = "Aircraft Engine Type Code", order = 9)
    public String getAircraftEngineTypeCode() {
        return aircraftEngineTypeCode;
    }
    public void setAircraftEngineTypeCode(String aircraftEngineTypeCode) {
        this.aircraftEngineTypeCode = aircraftEngineTypeCode;
    }

    @CsvColumn(name = "Aircraft Engine Type Code Description", order = 9)
    public String getAircraftEngineTypeCodeDescription() {
        return aircraftEngineTypeCodeDescription;
    }
    public void setAircraftEngineTypeCodeDescription(String aircraftEngineTypeCodeDescription) {
        this.aircraftEngineTypeCodeDescription = aircraftEngineTypeCodeDescription;
    }

    @CsvColumn(name = "Operating Status Code", order = 5)
    public String getOperatingStatusCode() {
        return operatingStatusCode;
    }
    public void setOperatingStatusCode(String operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    @CsvColumn(name = "Operating Status Code Description", order = 5)
    public String getOperatingStatusCodeDescription() {
        return operatingStatusCodeDescription;
    }
    public void setOperatingStatusCodeDescription(String operatingStatusCodeDescription) {
        this.operatingStatusCodeDescription = operatingStatusCodeDescription;
    }

    @CsvColumn(name = "Emissions Process Identifier", order = 2)
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

    @CsvColumn(name = "Status Year", order = 6)
    public String getStatusYear() {
        return statusYear;
    }
    public void setStatusYear(String statusYear) {
        this.statusYear = statusYear;
    }

    @CsvColumn(name = "SCC Code", order = 8)
    public String getSccCode() {
        return sccCode;
    }
    public void setSccCode(String sccCode) {
        this.sccCode = sccCode;
    }

    @CsvColumn(name = "SCC Description", order = 7)
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

    @CsvColumn(name = "Description", order = 4)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @CsvColumn(name = "Comments", order = 10)
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}

