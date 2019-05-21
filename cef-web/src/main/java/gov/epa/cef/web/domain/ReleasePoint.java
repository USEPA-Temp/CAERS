package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ReleasePoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "release_point", schema = "public")

public class ReleasePoint implements java.io.Serializable {

    // Fields

    private Long id;
    private ProgramSystemCode programSystemCode;
    private OperatingStatusCode operatingStatusCode;
    private Facility facility;
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
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private Set<ReleasePointAppt> releasePointAppts = new HashSet<ReleasePointAppt>(0);

    // Constructors

    /** default constructor */
    public ReleasePoint() {
    }

    /** minimal constructor */
    public ReleasePoint(Long id, ProgramSystemCode programSystemCode, OperatingStatusCode operatingStatusCode,
            Facility facility, String releasePointIdentifier, String typeCode, String description, Double latitude,
            Double longitude, String createdBy, Timestamp createdDate, String lastModifiedBy,
            Timestamp lastModifiedDate) {
        this.id = id;
        this.programSystemCode = programSystemCode;
        this.operatingStatusCode = operatingStatusCode;
        this.facility = facility;
        this.releasePointIdentifier = releasePointIdentifier;
        this.typeCode = typeCode;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public ReleasePoint(Long id, ProgramSystemCode programSystemCode, OperatingStatusCode operatingStatusCode,
            Facility facility, String releasePointIdentifier, String typeCode, String description, Double stackHeight,
            String stackHeightUomCode, Double stackDiameter, String stackDiameterUomCode, Double exitGasVelocity,
            String exitGasVelicityUomCode, Short exitGasTemperature, Double exitGasFlowRate, String exitGasFlowUomCode,
            Short statusYear, Double latitude, Double longitude, String createdBy, Timestamp createdDate,
            String lastModifiedBy, Timestamp lastModifiedDate, Set<ReleasePointAppt> releasePointAppts) {
        this.id = id;
        this.programSystemCode = programSystemCode;
        this.operatingStatusCode = operatingStatusCode;
        this.facility = facility;
        this.releasePointIdentifier = releasePointIdentifier;
        this.typeCode = typeCode;
        this.description = description;
        this.stackHeight = stackHeight;
        this.stackHeightUomCode = stackHeightUomCode;
        this.stackDiameter = stackDiameter;
        this.stackDiameterUomCode = stackDiameterUomCode;
        this.exitGasVelocity = exitGasVelocity;
        this.exitGasVelicityUomCode = exitGasVelicityUomCode;
        this.exitGasTemperature = exitGasTemperature;
        this.exitGasFlowRate = exitGasFlowRate;
        this.exitGasFlowUomCode = exitGasFlowUomCode;
        this.statusYear = statusYear;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.releasePointAppts = releasePointAppts;
    }

    // Property accessors
    @Id

    @Column(name = "id", unique = true, nullable = false)

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_system_code", nullable = false)

    public ProgramSystemCode getProgramSystemCode() {
        return this.programSystemCode;
    }

    public void setProgramSystemCode(ProgramSystemCode programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)

    public OperatingStatusCode getOperatingStatusCode() {
        return this.operatingStatusCode;
    }

    public void setOperatingStatusCode(OperatingStatusCode operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)

    public Facility getFacility() {
        return this.facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Column(name = "release_point_identifier", nullable = false, length = 20)

    public String getReleasePointIdentifier() {
        return this.releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    @Column(name = "type_code", nullable = false, length = 20)

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "description", nullable = false, length = 200)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "stack_height", precision = 6, scale = 3)

    public Double getStackHeight() {
        return this.stackHeight;
    }

    public void setStackHeight(Double stackHeight) {
        this.stackHeight = stackHeight;
    }

    @Column(name = "stack_height_uom_code", length = 20)

    public String getStackHeightUomCode() {
        return this.stackHeightUomCode;
    }

    public void setStackHeightUomCode(String stackHeightUomCode) {
        this.stackHeightUomCode = stackHeightUomCode;
    }

    @Column(name = "stack_diameter", precision = 6, scale = 3)

    public Double getStackDiameter() {
        return this.stackDiameter;
    }

    public void setStackDiameter(Double stackDiameter) {
        this.stackDiameter = stackDiameter;
    }

    @Column(name = "stack_diameter_uom_code", length = 20)

    public String getStackDiameterUomCode() {
        return this.stackDiameterUomCode;
    }

    public void setStackDiameterUomCode(String stackDiameterUomCode) {
        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    @Column(name = "exit_gas_velocity", precision = 8, scale = 3)

    public Double getExitGasVelocity() {
        return this.exitGasVelocity;
    }

    public void setExitGasVelocity(Double exitGasVelocity) {
        this.exitGasVelocity = exitGasVelocity;
    }

    @Column(name = "exit_gas_velicity_uom_code", length = 20)

    public String getExitGasVelicityUomCode() {
        return this.exitGasVelicityUomCode;
    }

    public void setExitGasVelicityUomCode(String exitGasVelicityUomCode) {
        this.exitGasVelicityUomCode = exitGasVelicityUomCode;
    }

    @Column(name = "exit_gas_temperature", precision = 4, scale = 0)

    public Short getExitGasTemperature() {
        return this.exitGasTemperature;
    }

    public void setExitGasTemperature(Short exitGasTemperature) {
        this.exitGasTemperature = exitGasTemperature;
    }

    @Column(name = "exit_gas_flow_rate", precision = 16, scale = 8)

    public Double getExitGasFlowRate() {
        return this.exitGasFlowRate;
    }

    public void setExitGasFlowRate(Double exitGasFlowRate) {
        this.exitGasFlowRate = exitGasFlowRate;
    }

    @Column(name = "exit_gas_flow_uom_code", length = 20)

    public String getExitGasFlowUomCode() {
        return this.exitGasFlowUomCode;
    }

    public void setExitGasFlowUomCode(String exitGasFlowUomCode) {
        this.exitGasFlowUomCode = exitGasFlowUomCode;
    }

    @Column(name = "status_year")

    public Short getStatusYear() {
        return this.statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "created_by", nullable = false)

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_date", nullable = false, length = 29)

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_modified_by", nullable = false)

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "last_modified_date", nullable = false, length = 29)

    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "releasePoint")

    public Set<ReleasePointAppt> getReleasePointAppts() {
        return this.releasePointAppts;
    }

    public void setReleasePointAppts(Set<ReleasePointAppt> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
    }

}