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

@CsvFileName(name = "release_points.csv")
public class ReleasePointBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    @NotBlank(message = "Description is required.")
    @Size(max = 200, message = "Description can not exceed {max} chars; found '${validatedValue}'.")
    private String description;

    @Pattern(regexp = "^\\d{0,8}(\\.\\d{1,8})?$",
        message = "Exit Gas Flow Rate is not in expected numeric format: '{8}.{8}' digits; found '${validatedValue}'.")
    private String exitGasFlowRate;

    @Size(max = 20, message = "Exit Gas Flow Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String exitGasFlowUomCode;

    @Pattern(regexp = "^[+-]?\\d{0,4}$",
        message = "Exit Gas Temperature is not in expected format: '+/-{4}' digits; found '${validatedValue}'.")
    private String exitGasTemperature;

    @Pattern(regexp = "^\\d{0,5}(\\.\\d{1,3})?$",
        message = "Exit Gas Velocity is not in expected numeric format: '{5}.{3}' digits; found '${validatedValue}'.")
    private String exitGasVelocity;

    @Size(max = 20, message = "Exit Gas Velocity Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String exitGasVelocityUomCode;

    @NotNull(message = "Release Point ID is required.")
    private Long facilitySiteId;

    @Pattern(regexp = "^\\d{0,6}$",
        message = "Fence Line Distance is not in expected format: {10} digits; found '${validatedValue}'.")
    private String fenceLineDistance;

    @Size(max = 20, message = "Fence Line Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fenceLineUomCode;

    @Pattern(regexp = "^\\d{0,3}$",
        message = "Fugitive Angle is not in expected numeric format: {3} digits; found '${validatedValue}'.")
    private String fugitiveAngle;

    @Pattern(regexp = "^\\d{0,3}$",
        message = "Fugitive Height is not in expected numeric format: {3} digits; found '${validatedValue}'.")
    private String fugitiveHeight;

    @Size(max = 20, message = "Fugitive Height Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fugitiveHeightUomCode;

    @Pattern(regexp = "^\\d{0,5}(\\.\\d{1,3})?$",
        message = "Fugitive Length is not in expected numeric format: '{5}.{3}' digits; found '${validatedValue}'.")
    private String fugitiveLength;
    
    @Size(max = 20, message = "Fugitive Length Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'; found '${validatedValue}'.")
    private String fugitiveLengthUomCode;
    
    @Pattern(regexp = "^\\d{0,5}(\\.\\d{1,3})?$",
        message = "Fugitive Width is not in expected numeric format: '{5}.{3}' digits; found '${validatedValue}'.")
    private String fugitiveWidth;

    @Size(max = 20, message = "Fugitive Width Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fugitiveWidthUomCode;

    @Pattern(regexp = LatitudePattern,
        message = "Fugitive Mid Point 2 Latitude is not in expected numeric format:'+/-{2}.{6}' digits; found '${validatedValue}'.")
    private String fugitiveLine2Latitude;

    @Pattern(regexp = LongitudePattern,
        message = "Fugitive Mid Point 2 Longitude is not in expected numeric format: '+/-{3}.{6}' digits; found '${validatedValue}'.")
    private String fugitiveLine2Longitude;

    @NotNull(message = "Release Point ID is required.")
    private Long id;

    @Pattern(regexp = LatitudePattern,
        message = "Latitude is not in expected numeric format: '+/-{2}.{6}' digits; found '${validatedValue}'.")
    private String latitude;

    @Pattern(regexp = LongitudePattern,
        message = "Longitude is not in expected numeric format: '+/-{3}.{6}' digits; found '${validatedValue}'.")
    private String longitude;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCode;
    
    private String operatingStatusDescription;

    @NotBlank(message = "Release Point Identifier is required.")
    @Size(max = 20, message = "Release Point Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String releasePointIdentifier;

    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,3})?$",
        message = "Stack Diameter is not in expected numeric format: '{3}.{3}' digits; found '${validatedValue}'.")
    private String stackDiameter;

    @Size(max = 20, message = "Stack Diameter Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackDiameterUomCode;

    @Pattern(regexp = "^\\d{0,4}(\\.\\d)?$",
        message = "Stack Height is not in expected numeric format: '{4}.{1}' digits; found '${validatedValue}'.")
    private String stackHeight;

    @Size(max = 20, message = "Stack Height Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackHeightUomCode;

    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,3})?$",
        message = "Stack Width is not in expected numeric format: '{3}.{3}' digits; found '${validatedValue}'.")
    private String stackWidth;

    @Size(max = 20, message = "Stack Width Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackWidthUomCode;

    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,3})?$",
        message = "Stack Length is not in expected numeric format: '{3}.{3}' digits; found '${validatedValue}'.")
    private String stackLength;

    @Size(max = 20, message = "Stack Length Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackLengthUomCode;

    @NotBlank(message = "Operating Status Year is required.")
    @Pattern(regexp = YearPattern,
        message = "Operating Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    @NotBlank(message = "Type Code is required.")
    @Size(max = 20, message = "Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String typeCode;
    
    private String typeDescription;

    public ReleasePointBulkUploadDto() {

        super(WorksheetName.ReleasePoint);
    }

    @CsvColumn(name = "Comments", order = 34)
    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

    @CsvColumn(name = "Description", order = 5)
    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    @CsvColumn(name = "Exit Gas Flow Rate", order = 23)
    public String getExitGasFlowRate() {

        return exitGasFlowRate;
    }

    public void setExitGasFlowRate(String exitGasFlowRate) {

        this.exitGasFlowRate = exitGasFlowRate;
    }

    @CsvColumn(name = "Exit Gas Flow Rate UOM", order = 24)
    public String getExitGasFlowUomCode() {

        return exitGasFlowUomCode;
    }

    public void setExitGasFlowUomCode(String exitGasFlowUomCode) {

        this.exitGasFlowUomCode = exitGasFlowUomCode;
    }

    @CsvColumn(name = "Exit Gas Temperature", order = 22)
    public String getExitGasTemperature() {

        return exitGasTemperature;
    }

    public void setExitGasTemperature(String exitGasTemperature) {

        this.exitGasTemperature = exitGasTemperature;
    }

    @CsvColumn(name = "Exit Gas Velocity", order = 20)
    public String getExitGasVelocity() {

        return exitGasVelocity;
    }

    public void setExitGasVelocity(String exitGasVelocity) {

        this.exitGasVelocity = exitGasVelocity;
    }

    @CsvColumn(name = "Exit Gas Velocity UOM", order = 21)
    public String getExitGasVelocityUomCode() {

        return exitGasVelocityUomCode;
    }

    public void setExitGasVelocityUomCode(String exitGasVelicityUomCode) {

        this.exitGasVelocityUomCode = exitGasVelicityUomCode;
    }

    @CsvColumn(name = "Facility Site ID", order = 1)
    public Long getFacilitySiteId() {

        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {

        this.facilitySiteId = facilitySiteId;
    }

    @CsvColumn(name = "Fence Line Distance", order = 25)
    public String getFenceLineDistance() {

        return fenceLineDistance;
    }

    public void setFenceLineDistance(String fenceLineDistance) {

        this.fenceLineDistance = fenceLineDistance;
    }

    @CsvColumn(name = "Fence Line Distance UOM", order = 26)
    public String getFenceLineUomCode() {

        return fenceLineUomCode;
    }

    public void setFenceLineUomCode(String fenceLineUomCode) {

        this.fenceLineUomCode = fenceLineUomCode;
    }

    @CsvColumn(name = "Fugitive Angle", order = 33)
    public String getFugitiveAngle() {

        return fugitiveAngle;
    }

    public void setFugitiveAngle(String fugitiveAngle) {

        this.fugitiveAngle = fugitiveAngle;
    }

    @CsvColumn(name = "Fugitive Height", order = 27)
    public String getFugitiveHeight() {

        return fugitiveHeight;
    }

    public void setFugitiveHeight(String fugitiveHeight) {

        this.fugitiveHeight = fugitiveHeight;
    }

    @CsvColumn(name = "Fugitive Height UOM", order = 28)
    public String getFugitiveHeightUomCode() {

        return fugitiveHeightUomCode;
    }

    public void setFugitiveHeightUomCode(String fugitiveHeightUomCode) {

        this.fugitiveHeightUomCode = fugitiveHeightUomCode;
    }

    @CsvColumn(name = "Fugitive Length", order = 31)
    public String getFugitiveLength() {

        return fugitiveLength;
    }

    public void setFugitiveLength(String fugitiveLength) {

        this.fugitiveLength = fugitiveLength;
    }

    @CsvColumn(name = "Fugitive Length UOM", order = 32)
    public String getFugitiveLengthUomCode() {

        return fugitiveLengthUomCode;
    }

    public void setFugitiveLengthUomCode(String fugitiveLengthUomCode) {

        this.fugitiveLengthUomCode = fugitiveLengthUomCode;
    }

    @CsvColumn(name = "Fugitive Midpoint 2 Latitude", order = 10)
    public String getFugitiveLine2Latitude() {
		return fugitiveLine2Latitude;
	}

	public void setFugitiveLine2Latitude(String fugitiveLine2Latitude) {
		this.fugitiveLine2Latitude = fugitiveLine2Latitude;
	}

    @CsvColumn(name = "Fugitive Midpoint 2 Longitude", order = 11)
	public String getFugitiveLine2Longitude() {
		return fugitiveLine2Longitude;
	}

	public void setFugitiveLine2Longitude(String fugitiveLine2Longitude) {
		this.fugitiveLine2Longitude = fugitiveLine2Longitude;
	}

    @CsvColumn(name = "Fugitive Width", order = 29)
    public String getFugitiveWidth() {

        return fugitiveWidth;
    }

    public void setFugitiveWidth(String fugitiveWidth) {

        this.fugitiveWidth = fugitiveWidth;
    }

    @CsvColumn(name = "Fugitive Width UOM", order = 30)
    public String getFugitiveWidthUomCode() {

        return fugitiveWidthUomCode;
    }

    public void setFugitiveWidthUomCode(String fugitiveWidthUomCode) {

        this.fugitiveWidthUomCode = fugitiveWidthUomCode;
    }

    @CsvColumn(name = "ID", order = 3)
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @CsvColumn(name = "Latitude", order = 8)
    public String getLatitude() {

        return latitude;
    }

    public void setLatitude(String latitude) {

        this.latitude = latitude;
    }

    @CsvColumn(name = "Longitude", order = 9)
    public String getLongitude() {

        return longitude;
    }

    public void setLongitude(String longitude) {

        this.longitude = longitude;
    }

    @CsvColumn(name = "Operating Status Code", order = 6)
    public String getOperatingStatusCode() {

        return operatingStatusCode;
    }

    public void setOperatingStatusCode(String operatingStatusCode) {

        this.operatingStatusCode = operatingStatusCode;
    }

    @CsvColumn(name = "Operating Status Description", order = 6)
    public String getOperatingStatusDescription() {
        return operatingStatusDescription;
    }
    public void setOperatingStatusDescription(String operatingStatusDescription) {
        this.operatingStatusDescription = operatingStatusDescription;
    }

    @CsvColumn(name = "Release Point Identifier", order = 2)
    public String getReleasePointIdentifier() {

        return releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {

        this.releasePointIdentifier = releasePointIdentifier;
    }

    @CsvColumn(name = "Stack Diameter", order = 14)
    public String getStackDiameter() {

        return stackDiameter;
    }

    public void setStackDiameter(String stackDiameter) {

        this.stackDiameter = stackDiameter;
    }

    @CsvColumn(name = "Stack Diameter UOM", order = 15)
    public String getStackDiameterUomCode() {

        return stackDiameterUomCode;
    }

    public void setStackDiameterUomCode(String stackDiameterUomCode) {

        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    @CsvColumn(name = "Stack Height", order = 12)
    public String getStackHeight() {

        return stackHeight;
    }

    public void setStackHeight(String stackHeight) {

        this.stackHeight = stackHeight;
    }

    @CsvColumn(name = "Stack Height UOM", order = 13)
    public String getStackHeightUomCode() {

        return stackHeightUomCode;
    }

    public void setStackHeightUomCode(String stackHeightUomCode) {

        this.stackHeightUomCode = stackHeightUomCode;
    }

    @CsvColumn(name = "Stack Width", order = 16)
    public String getStackWidth() {
        return stackWidth;
    }

    public void setStackWidth(String stackWidth) {
        this.stackWidth = stackWidth;
    }

    @CsvColumn(name = "Stack Width UOM", order = 17)
    public String getStackWidthUomCode() {
        return stackWidthUomCode;
    }

    public void setStackWidthUomCode(String stackWidthUomCode) {
        this.stackWidthUomCode = stackWidthUomCode;
    }

    @CsvColumn(name = "Stack Length", order = 18)
    public String getStackLength() {
        return stackLength;
    }

    public void setStackLength(String stackLength) {
        this.stackLength = stackLength;
    }

    @CsvColumn(name = "Stack Length UOM", order = 19)
    public String getStackLengthUomCode() {
        return stackLengthUomCode;
    }

    public void setStackLengthUomCode(String stackLengthUomCode) {
        this.stackLengthUomCode = stackLengthUomCode;
    }

    @CsvColumn(name = "Status Year", order = 7)
    public String getStatusYear() {

        return statusYear;
    }

    public void setStatusYear(String statusYear) {

        this.statusYear = statusYear;
    }

    @CsvColumn(name = "Type Code", order = 4)
    public String getTypeCode() {

        return typeCode;
    }

    public void setTypeCode(String typeCode) {

        this.typeCode = typeCode;
    }

    @CsvColumn(name = "Type Description", order = 4)
    public String getTypeDescription() {

        return typeDescription;
    }
    public void setTypeDescription(String typeDescription) {

        this.typeDescription = typeDescription;
    }

}
