package gov.epa.cef.web.service.dto;

import java.io.Serializable;

import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ProgramSystemCode;

public class ReleasePointDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ProgramSystemCode programSystemCode;
    private OperatingStatusCode operatingStatusCode;
    private String releasePointIdentifier;
    private String typeCode;
    private String description;
    private Double stackHeight;
    private String stackHeightUomCode;
    private Double stackDiameter;
    private String stackDiameterUomCode;
    private Double exitGasVelocity;
    private String exitGasVelicityUomCode;
    private Short exitGasTemperature;
    private Double exitGasFlowRate;
    private String exitGasFlowUomCode;
    private Short statusYear;
    private Double latitude;
    private Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProgramSystemCode getProgramSystemCode() {
        return programSystemCode;
    }

    public void setProgramSystemCode(ProgramSystemCode programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public OperatingStatusCode getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
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

    public String getExitGasVelicityUomCode() {
        return exitGasVelicityUomCode;
    }

    public void setExitGasVelicityUomCode(String exitGasVelicityUomCode) {
        this.exitGasVelicityUomCode = exitGasVelicityUomCode;
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

}