package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

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

    @Pattern(regexp = "^\\d{0,4}$",
        message = "Exit Gas Temperature is not in expected format: {4} digits; found '${validatedValue}'.")
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

    @Pattern(regexp = "^\\d{0,6}$",
        message = "Fugitive Length is not in expected numeric format: {6} digits; found '${validatedValue}'.")
    private String fugitiveLength;

    @Size(max = 20, message = "Fugitive Length Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'; found '${validatedValue}'.")
    private String fugitiveLengthUomCode;

    @Pattern(regexp = LatitudePattern,
        message = "Fugitive Line1 Latitude is not in expected numeric format: '+/-{2}.{6}' digits; found '${validatedValue}'.")
    private String fugitiveLine1Latitude;

    @Pattern(regexp = LongitudePattern,
        message = "Fugitive Line1 Longitude is not in expected numeric format: '+/-{3}.{6}' digits; found '${validatedValue}'.")
    private String fugitiveLine1Longitude;

    @Pattern(regexp = LatitudePattern,
        message = "Fugitive Line2 Latitude is not in expected numeric format:'+/-{2}.{6}' digits; found '${validatedValue}'.")
    private String fugitiveLine2Latitude;

    @Pattern(regexp = LongitudePattern,
        message = "Fugitive Line2 Longitude is not in expected numeric format: '+/-{3}.{6}' digits; found '${validatedValue}'.")
    private String fugitiveLine2Longitude;

    @Pattern(regexp = "^\\d{0,6}$",
        message = "Fugitive Width is not in expected numeric format: {6} digits; found '${validatedValue}'.")
    private String fugitiveWidth;

    @Size(max = 20, message = "Fugitive Width Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fugitiveWidthUomCode;

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

    @NotBlank(message = "Release Point Identifier is required.")
    @Size(max = 20, message = "Release Point Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String releasePointIdentifier;

    @Pattern(regexp = "^\\d{0,3}(\\.\\d{1,3})?$",
        message = "Stack Diameter is not in expected numeric format: '{3}.{3}' digits.")
    private String stackDiameter;

    @Size(max = 20, message = "Stack Diameter Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackDiameterUomCode;

    @Pattern(regexp = "^\\d{0,5}(\\.\\d{1,3})?$",
        message = "Stack Height is not in expected numeric format: '{5}.{3}' digits.")
    private String stackHeight;

    @Size(max = 20, message = "Stack Height Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackHeightUomCode;

    @Pattern(regexp = YearPattern,
        message = "Status Year is not in expected format: {4} digits; found '${validatedValue}'.")
    private String statusYear;

    @NotBlank(message = "Type Code is required.")
    @Size(max = 20, message = "Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String typeCode;

    public ReleasePointBulkUploadDto() {

        super(WorksheetName.ReleasePoint);
    }

    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getExitGasFlowRate() {

        return exitGasFlowRate;
    }

    public void setExitGasFlowRate(String exitGasFlowRate) {

        this.exitGasFlowRate = exitGasFlowRate;
    }

    public String getExitGasFlowUomCode() {

        return exitGasFlowUomCode;
    }

    public void setExitGasFlowUomCode(String exitGasFlowUomCode) {

        this.exitGasFlowUomCode = exitGasFlowUomCode;
    }

    public String getExitGasTemperature() {

        return exitGasTemperature;
    }

    public void setExitGasTemperature(String exitGasTemperature) {

        this.exitGasTemperature = exitGasTemperature;
    }

    public String getExitGasVelocity() {

        return exitGasVelocity;
    }

    public void setExitGasVelocity(String exitGasVelocity) {

        this.exitGasVelocity = exitGasVelocity;
    }

    public String getExitGasVelocityUomCode() {

        return exitGasVelocityUomCode;
    }

    public void setExitGasVelocityUomCode(String exitGasVelicityUomCode) {

        this.exitGasVelocityUomCode = exitGasVelicityUomCode;
    }

    public Long getFacilitySiteId() {

        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {

        this.facilitySiteId = facilitySiteId;
    }

    public String getFenceLineDistance() {

        return fenceLineDistance;
    }

    public void setFenceLineDistance(String fenceLineDistance) {

        this.fenceLineDistance = fenceLineDistance;
    }

    public String getFenceLineUomCode() {

        return fenceLineUomCode;
    }

    public void setFenceLineUomCode(String fenceLineUomCode) {

        this.fenceLineUomCode = fenceLineUomCode;
    }

    public String getFugitiveAngle() {

        return fugitiveAngle;
    }

    public void setFugitiveAngle(String fugitiveAngle) {

        this.fugitiveAngle = fugitiveAngle;
    }

    public String getFugitiveHeight() {

        return fugitiveHeight;
    }

    public void setFugitiveHeight(String fugitiveHeight) {

        this.fugitiveHeight = fugitiveHeight;
    }

    public String getFugitiveHeightUomCode() {

        return fugitiveHeightUomCode;
    }

    public void setFugitiveHeightUomCode(String fugitiveHeightUomCode) {

        this.fugitiveHeightUomCode = fugitiveHeightUomCode;
    }

    public String getFugitiveLength() {

        return fugitiveLength;
    }

    public void setFugitiveLength(String fugitiveLength) {

        this.fugitiveLength = fugitiveLength;
    }

    public String getFugitiveLengthUomCode() {

        return fugitiveLengthUomCode;
    }

    public void setFugitiveLengthUomCode(String fugitiveLengthUomCode) {

        this.fugitiveLengthUomCode = fugitiveLengthUomCode;
    }

    public String getFugitiveLine1Latitude() {

        return fugitiveLine1Latitude;
    }

    public void setFugitiveLine1Latitude(String fugitiveLine1Latitude) {

        this.fugitiveLine1Latitude = fugitiveLine1Latitude;
    }

    public String getFugitiveLine1Longitude() {

        return fugitiveLine1Longitude;
    }

    public void setFugitiveLine1Longitude(String fugitiveLine1Longitude) {

        this.fugitiveLine1Longitude = fugitiveLine1Longitude;
    }

    public String getFugitiveLine2Latitude() {

        return fugitiveLine2Latitude;
    }

    public void setFugitiveLine2Latitude(String fugitiveLine2Latitude) {

        this.fugitiveLine2Latitude = fugitiveLine2Latitude;
    }

    public String getFugitiveLine2Longitude() {

        return fugitiveLine2Longitude;
    }

    public void setFugitiveLine2Longitude(String fugitiveLine2Longitude) {

        this.fugitiveLine2Longitude = fugitiveLine2Longitude;
    }

    public String getFugitiveWidth() {

        return fugitiveWidth;
    }

    public void setFugitiveWidth(String fugitiveWidth) {

        this.fugitiveWidth = fugitiveWidth;
    }

    public String getFugitiveWidthUomCode() {

        return fugitiveWidthUomCode;
    }

    public void setFugitiveWidthUomCode(String fugitiveWidthUomCode) {

        this.fugitiveWidthUomCode = fugitiveWidthUomCode;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getLatitude() {

        return latitude;
    }

    public void setLatitude(String latitude) {

        this.latitude = latitude;
    }

    public String getLongitude() {

        return longitude;
    }

    public void setLongitude(String longitude) {

        this.longitude = longitude;
    }

    public String getOperatingStatusCode() {

        return operatingStatusCode;
    }

    public void setOperatingStatusCode(String operatingStatusCode) {

        this.operatingStatusCode = operatingStatusCode;
    }

    public String getReleasePointIdentifier() {

        return releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {

        this.releasePointIdentifier = releasePointIdentifier;
    }

    public String getStackDiameter() {

        return stackDiameter;
    }

    public void setStackDiameter(String stackDiameter) {

        this.stackDiameter = stackDiameter;
    }

    public String getStackDiameterUomCode() {

        return stackDiameterUomCode;
    }

    public void setStackDiameterUomCode(String stackDiameterUomCode) {

        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    public String getStackHeight() {

        return stackHeight;
    }

    public void setStackHeight(String stackHeight) {

        this.stackHeight = stackHeight;
    }

    public String getStackHeightUomCode() {

        return stackHeightUomCode;
    }

    public void setStackHeightUomCode(String stackHeightUomCode) {

        this.stackHeightUomCode = stackHeightUomCode;
    }

    public String getStatusYear() {

        return statusYear;
    }

    public void setStatusYear(String statusYear) {

        this.statusYear = statusYear;
    }

    public String getTypeCode() {

        return typeCode;
    }

    public void setTypeCode(String typeCode) {

        this.typeCode = typeCode;
    }

}
