package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ReleasePointDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long facilitySiteId;
    private CodeLookupDto programSystemCode;
    private CodeLookupDto operatingStatusCode;
    private String releasePointIdentifier;
    private CodeLookupDto typeCode;
    private String description;
    private Double stackHeight;
    private CodeLookupDto stackHeightUomCode;
    private Double stackDiameter;
    private CodeLookupDto stackDiameterUomCode;
    private Double exitGasVelocity;
    private CodeLookupDto exitGasVelocityUomCode;
    private Short exitGasTemperature;
    private Double exitGasFlowRate;
    private CodeLookupDto exitGasFlowUomCode;
    private Short statusYear;
    private Double fugitiveLine1Latitude;
    private Double fugitiveLine1Longitude;
    private Double fugitiveLine2Latitude;
    private Double fugitiveLine2Longitude;
    private Double latitude;
    private Double longitude;
    private String comments;

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

    public CodeLookupDto getProgramSystemCode() {
        return programSystemCode;
    }

    public void setProgramSystemCode(CodeLookupDto programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public CodeLookupDto getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(CodeLookupDto operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public String getReleasePointIdentifier() {
        return releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public CodeLookupDto getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(CodeLookupDto typeCode) {
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

    public CodeLookupDto getStackHeightUomCode() {
        return stackHeightUomCode;
    }

    public void setStackHeightUomCode(CodeLookupDto stackHeightUomCode) {
        this.stackHeightUomCode = stackHeightUomCode;
    }

    public Double getStackDiameter() {
        return stackDiameter;
    }

    public void setStackDiameter(Double stackDiameter) {
        this.stackDiameter = stackDiameter;
    }

    public CodeLookupDto getStackDiameterUomCode() {
        return stackDiameterUomCode;
    }

    public void setStackDiameterUomCode(CodeLookupDto stackDiameterUomCode) {
        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    public Double getExitGasVelocity() {
        return exitGasVelocity;
    }

    public void setExitGasVelocity(Double exitGasVelocity) {
        this.exitGasVelocity = exitGasVelocity;
    }

    public CodeLookupDto getExitGasVelocityUomCode() {
        return exitGasVelocityUomCode;
    }

    public void setExitGasVelocityUomCode(CodeLookupDto exitGasVelicityUomCode) {
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

    public CodeLookupDto getExitGasFlowUomCode() {
        return exitGasFlowUomCode;
    }

    public void setExitGasFlowUomCode(CodeLookupDto exitGasFlowUomCode) {
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

}
