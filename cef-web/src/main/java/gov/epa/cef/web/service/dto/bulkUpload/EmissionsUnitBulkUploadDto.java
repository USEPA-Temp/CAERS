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

public class EmissionsUnitBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @NotBlank(message = "Description is required.")
    @Size(max = 100, message = "Description can not exceed {max} chars; found '${validatedValue}'.")
    private String description;

    @Pattern(regexp = PositiveDecimalPattern,
        message = "Design Capacity is not in expected numeric format; found '${validatedValue}'.")
    private String designCapacity;

    @NotNull(message = "Facility Site ID is required.")
    private Long facilitySiteId;

    @NotNull(message = "Emissions Unit ID is required.")
    private Long id;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCodeDescription;

    @NotBlank(message = "Operating Status Year is required.")
    @Pattern(regexp = YearPattern,
        message = "Operating Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    @NotBlank(message = "Type Code is required.")
    @Size(max = 20, message = "Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String typeCode;

    @NotBlank(message = "Unit Identifier is required.")
    @Size(max = 20, message = "Unit Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String unitIdentifier;

    @Size(max = 20, message = "Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String unitOfMeasureCode;

    public EmissionsUnitBulkUploadDto() {

        super(WorksheetName.EmissionsUnit);
    }

    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getDesignCapacity() {

        return designCapacity;
    }

    public void setDesignCapacity(String designCapacity) {

        this.designCapacity = designCapacity;
    }

    public Long getFacilitySiteId() {

        return this.facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {

        this.facilitySiteId = facilitySiteId;
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getOperatingStatusCodeDescription() {

        return this.operatingStatusCodeDescription;
    }

    public void setOperatingStatusCodeDescription(String operatingStatusCodeDescription) {

        this.operatingStatusCodeDescription = operatingStatusCodeDescription;
    }

    public String getStatusYear() {

        return this.statusYear;
    }

    public void setStatusYear(String statusYear) {

        this.statusYear = statusYear;
    }

    public String getTypeCode() {

        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {

        this.typeCode = typeCode;
    }

    public String getUnitIdentifier() {

        return this.unitIdentifier;
    }

    public void setUnitIdentifier(String unitIdentifier) {

        this.unitIdentifier = unitIdentifier;
    }

    public String getUnitOfMeasureCode() {

        return this.unitOfMeasureCode;
    }

    public void setUnitOfMeasureCode(String unitOfMeasureCode) {

        this.unitOfMeasureCode = unitOfMeasureCode;
    }
}
