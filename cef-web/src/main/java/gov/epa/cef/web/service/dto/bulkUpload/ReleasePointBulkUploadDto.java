package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ReleasePointBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Release Point ID is required.")
    private Long id;

    @NotNull(message = "Release Point ID is required.")
    private Long facilitySiteId;

    @NotBlank(message = "Release Point Identifier is required.")
    @Size(max = 20, message = "Release Point Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String releasePointIdentifier;

    @NotBlank(message = "Type Code is required.")
    @Size(max = 20, message = "Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String typeCode;

    @NotBlank(message = "Description is required.")
    @Size(max = 200, message = "Description can not exceed {max} chars; found '${validatedValue}'.")
    private String description;

    @Digits(integer = 5, fraction = 3,
        message = "Stack Height is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double stackHeight;

    @Size(max = 20, message = "Stack Height Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackHeightUomCode;

    @Digits(integer = 5, fraction = 3,
        message = "Stack Diameter is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double stackDiameter;

    @Size(max = 20, message = "Stack Diameter Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String stackDiameterUomCode;

    @Digits(integer = 5, fraction = 3,
        message = "Exit Gas Velocity is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double exitGasVelocity;

    @Size(max = 20, message = "Exit Gas Velocity Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String exitGasVelocityUomCode;

    private Short exitGasTemperature;

    @Digits(integer = 8, fraction = 8,
        message = "Exit Gas Flow Rate is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double exitGasFlowRate;

    @Size(max = 20, message = "Exit Gas Flow Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String exitGasFlowUomCode;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCode;

    private Short statusYear;

    @Digits(integer = 4, fraction = 6,
        message = "Latitude is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double latitude;

    @Digits(integer = 4, fraction = 6,
        message = "Longitude is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double longitude;

    @Digits(integer = 4, fraction = 6,
        message = "Fugitive Line1 Latitude is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double fugitiveLine1Latitude;

    @Digits(integer = 4, fraction = 6,
        message = "Fugitive Line1 Longitude is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double fugitiveLine1Longitude;

    @Digits(integer = 4, fraction = 6,
        message = "Fugitive Line2 Longitude is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double fugitiveLine2Latitude;

    @Digits(integer = 4, fraction = 6,
        message = "Fugitive Line2 Longitude is not in expected numeric format: '{integer}.{fraction}' digits.")
    private Double fugitiveLine2Longitude;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    private Long fenceLineDistance;

    @Size(max = 20, message = "Fence Line Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fenceLineUomCode;

    private Long fugitiveHeight;

    @Size(max = 20, message = "Fugitive Height Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fugitiveHeightUomCode;

    private Long fugitiveWidth;

    @Size(max = 20, message = "Fugitive Width Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fugitiveWidthUomCode;

    private Long fugitiveLength;

    @Size(max = 20, message = "Fugitive Length Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String fugitiveLengthUomCode;

    private Long fugitiveAngle;

    public ReleasePointBulkUploadDto() {

        super(WorksheetName.ReleasePoint);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilitySiteId() {
        return facilitySiteId;
    }
    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
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

    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStackHeight() {
        return stackHeight;
    }
    public void setStackHeight(Double stackHeight) {
        this.stackHeight = stackHeight;
    }

    public String getStackHeightUomCode() {
        return stackHeightUomCode;
    }
    public void setStackHeightUomCode(String stackHeightUomCode) {
        this.stackHeightUomCode = stackHeightUomCode;
    }

    public Double getStackDiameter() {
        return stackDiameter;
    }
    public void setStackDiameter(Double stackDiameter) {
        this.stackDiameter = stackDiameter;
    }

    public String getStackDiameterUomCode() {
        return stackDiameterUomCode;
    }
    public void setStackDiameterUomCode(String stackDiameterUomCode) {
        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    public Double getExitGasVelocity() {
        return exitGasVelocity;
    }
    public void setExitGasVelocity(Double exitGasVelocity) {
        this.exitGasVelocity = exitGasVelocity;
    }

    public String getExitGasVelocityUomCode() {
        return exitGasVelocityUomCode;
    }
    public void setExitGasVelocityUomCode(String exitGasVelicityUomCode) {
        this.exitGasVelocityUomCode = exitGasVelicityUomCode;
    }

    public Short getExitGasTemperature() {
        return exitGasTemperature;
    }
    public void setExitGasTemperature(Short exitGasTemperature) {
        this.exitGasTemperature = exitGasTemperature;
    }

    public Double getExitGasFlowRate() {
        return exitGasFlowRate;
    }
    public void setExitGasFlowRate(Double exitGasFlowRate) {
        this.exitGasFlowRate = exitGasFlowRate;
    }

    public String getExitGasFlowUomCode() {
        return exitGasFlowUomCode;
    }
    public void setExitGasFlowUomCode(String exitGasFlowUomCode) {
        this.exitGasFlowUomCode = exitGasFlowUomCode;
    }

    public Short getStatusYear() {
        return statusYear;
    }
    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public Double getFugitiveLine1Latitude() {
        return fugitiveLine1Latitude;
    }
    public void setFugitiveLine1Latitude(Double fugitiveLine1Latitude) {
        this.fugitiveLine1Latitude = fugitiveLine1Latitude;
    }

    public Double getFugitiveLine1Longitude() {
        return fugitiveLine1Longitude;
    }
    public void setFugitiveLine1Longitude(Double fugitiveLine1Longitude) {
        this.fugitiveLine1Longitude = fugitiveLine1Longitude;
    }

    public Double getFugitiveLine2Latitude() {
        return fugitiveLine2Latitude;
    }
    public void setFugitiveLine2Latitude(Double fugitiveLine2Latitude) {
        this.fugitiveLine2Latitude = fugitiveLine2Latitude;
    }

    public Double getFugitiveLine2Longitude() {
        return fugitiveLine2Longitude;
    }
    public void setFugitiveLine2Longitude(Double fugitiveLine2Longitude) {
        this.fugitiveLine2Longitude = fugitiveLine2Longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
		public Long getFenceLineDistance() {
			return fenceLineDistance;
		}
		public void setFenceLineDistance(Long fenceLineDistance) {
			this.fenceLineDistance = fenceLineDistance;
		}
		public String getFenceLineUomCode() {
			return fenceLineUomCode;
		}
		public void setFenceLineUomCode(String fenceLineUomCode) {
			this.fenceLineUomCode = fenceLineUomCode;
		}
		public Long getFugitiveHeight() {
			return fugitiveHeight;
		}
		public void setFugitiveHeight(Long fugitiveHeight) {
			this.fugitiveHeight = fugitiveHeight;
		}
		public String getFugitiveHeightUomCode() {
			return fugitiveHeightUomCode;
		}
		public void setFugitiveHeightUomCode(String fugitiveHeightUomCode) {
			this.fugitiveHeightUomCode = fugitiveHeightUomCode;
		}
		public Long getFugitiveWidth() {
			return fugitiveWidth;
		}
		public void setFugitiveWidth(Long fugitiveWidth) {
			this.fugitiveWidth = fugitiveWidth;
		}
		public String getFugitiveWidthUomCode() {
			return fugitiveWidthUomCode;
		}
		public void setFugitiveWidthUomCode(String fugitiveWidthUomCode) {
			this.fugitiveWidthUomCode = fugitiveWidthUomCode;
		}
		public Long getFugitiveLength() {
			return fugitiveLength;
		}
		public void setFugitiveLength(Long fugitiveLength) {
			this.fugitiveLength = fugitiveLength;
		}
		public String getFugitiveLengthUomCode() {
			return fugitiveLengthUomCode;
		}
		public void setFugitiveLengthUomCode(String fugitiveLengthUomCode) {
			this.fugitiveLengthUomCode = fugitiveLengthUomCode;
		}
		public Long getFugitiveAngle() {
			return fugitiveAngle;
		}
		public void setFugitiveAngle(Long fugitiveAngle) {
			this.fugitiveAngle = fugitiveAngle;
		}

}
