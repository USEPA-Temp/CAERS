package gov.epa.cef.web.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * ReleasePoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "release_point")

public class ReleasePoint extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_system_code", nullable = false)
    private ProgramSystemCode programSystemCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)
    private OperatingStatusCode operatingStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;
    
    @Column(name = "release_point_identifier", nullable = false, length = 20)
    private String releasePointIdentifier;
    
    @Column(name = "type_code", nullable = false, length = 20)
    private String typeCode;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "stack_height", precision = 6, scale = 3)
    private Double stackHeight;
    
    @Column(name = "stack_height_uom_code", length = 20)
    private String stackHeightUomCode;
    
    @Column(name = "stack_diameter", precision = 6, scale = 3)
    private Double stackDiameter;
    
    @Column(name = "stack_diameter_uom_code", length = 20)
    private String stackDiameterUomCode;
    
    @Column(name = "exit_gas_velocity", precision = 8, scale = 3)
    private Double exitGasVelocity;
    
    @Column(name = "exit_gas_velicity_uom_code", length = 20)
    private String exitGasVelicityUomCode;
    
    @Column(name = "exit_gas_temperature", precision = 4, scale = 0)
    private Short exitGasTemperature;
    
    @Column(name = "exit_gas_flow_rate", precision = 16, scale = 8)
    private Double exitGasFlowRate;
    
    @Column(name = "exit_gas_flow_uom_code", length = 20)
    private String exitGasFlowUomCode;
    
    @Column(name = "status_year")
    private Short statusYear;
    
    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private Double latitude;
    
    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private Double longitude;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "releasePoint")
    private Set<ReleasePointAppt> releasePointAppts = new HashSet<ReleasePointAppt>(0);

    // Constructors

    /** default constructor */
    public ReleasePoint() {
    }

    // Property accessors

    public ProgramSystemCode getProgramSystemCode() {
        return this.programSystemCode;
    }

    public void setProgramSystemCode(ProgramSystemCode programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public OperatingStatusCode getOperatingStatusCode() {
        return this.operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public Facility getFacility() {
        return this.facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getReleasePointIdentifier() {
        return this.releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStackHeight() {
        return this.stackHeight;
    }

    public void setStackHeight(Double stackHeight) {
        this.stackHeight = stackHeight;
    }

    public String getStackHeightUomCode() {
        return this.stackHeightUomCode;
    }

    public void setStackHeightUomCode(String stackHeightUomCode) {
        this.stackHeightUomCode = stackHeightUomCode;
    }

    public Double getStackDiameter() {
        return this.stackDiameter;
    }

    public void setStackDiameter(Double stackDiameter) {
        this.stackDiameter = stackDiameter;
    }

    public String getStackDiameterUomCode() {
        return this.stackDiameterUomCode;
    }

    public void setStackDiameterUomCode(String stackDiameterUomCode) {
        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    public Double getExitGasVelocity() {
        return this.exitGasVelocity;
    }

    public void setExitGasVelocity(Double exitGasVelocity) {
        this.exitGasVelocity = exitGasVelocity;
    }

    public String getExitGasVelicityUomCode() {
        return this.exitGasVelicityUomCode;
    }

    public void setExitGasVelicityUomCode(String exitGasVelicityUomCode) {
        this.exitGasVelicityUomCode = exitGasVelicityUomCode;
    }

    public Short getExitGasTemperature() {
        return this.exitGasTemperature;
    }

    public void setExitGasTemperature(Short exitGasTemperature) {
        this.exitGasTemperature = exitGasTemperature;
    }

    public Double getExitGasFlowRate() {
        return this.exitGasFlowRate;
    }

    public void setExitGasFlowRate(Double exitGasFlowRate) {
        this.exitGasFlowRate = exitGasFlowRate;
    }

    public String getExitGasFlowUomCode() {
        return this.exitGasFlowUomCode;
    }

    public void setExitGasFlowUomCode(String exitGasFlowUomCode) {
        this.exitGasFlowUomCode = exitGasFlowUomCode;
    }

    public Short getStatusYear() {
        return this.statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<ReleasePointAppt> getReleasePointAppts() {
        return this.releasePointAppts;
    }

    public void setReleasePointAppts(Set<ReleasePointAppt> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
    }

}