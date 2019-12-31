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
    @JoinColumn(name = "program_system_code")
    private ProgramSystemCode programSystemCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)
    private OperatingStatusCode operatingStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;
    
    @Column(name = "release_point_identifier", nullable = false, length = 20)
    private String releasePointIdentifier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code", nullable = false)
    private ReleasePointTypeCode typeCode;

    @Column(name = "description", nullable = false, length = 200)
    private String description;
    
    @Column(name = "fence_line_distance", precision = 6, scale = 0)
    private Long fenceLineDistance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fence_line_distance_uom_code", nullable = false)
    private UnitMeasureCode fenceLineUomCode;

    @Column(name = "stack_height", precision = 8, scale = 3)
    private Double stackHeight;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stack_height_uom_code", nullable = false)
    private UnitMeasureCode stackHeightUomCode;
    
    @Column(name = "stack_diameter", precision = 6, scale = 3)
    private Double stackDiameter;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stack_diameter_uom_code", nullable = false)
    private UnitMeasureCode stackDiameterUomCode;
    
    @Column(name = "exit_gas_velocity", precision = 8, scale = 3)
    private Double exitGasVelocity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exit_gas_velocity_uom_code", nullable = false)
    private UnitMeasureCode exitGasVelocityUomCode;
    
    @Column(name = "exit_gas_temperature", precision = 4, scale = 0)
    private Short exitGasTemperature;
    
    @Column(name = "exit_gas_flow_rate", precision = 16, scale = 8)
    private Double exitGasFlowRate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exit_gas_flow_uom_code", nullable = false)
    private UnitMeasureCode exitGasFlowUomCode;
    
    @Column(name = "status_year")
    private Short statusYear;
    
    @Column(name = "fugitive_line_1_latitude", precision = 10, scale = 6)
    private Double fugitiveLine1Latitude;
    
    @Column(name = "fugitive_line_1_longitude", precision = 10, scale = 6)
    private Double fugitiveLine1Longitude;
    
    @Column(name = "fugitive_line_2_latitude", precision = 10, scale = 6)
    private Double fugitiveLine2Latitude;
    
    @Column(name = "fugitive_line_2_longitude", precision = 10, scale = 6)
    private Double fugitiveLine2Longitude;
    
    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private Double latitude;
    
    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private Double longitude;
    
    @Column(name = "fugitive_height", precision = 3, scale = 0)
    private Long fugitiveHeight;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fugitive_height_uom_code", nullable = false)
    private UnitMeasureCode fugitiveHeightUomCode;
    
    @Column(name = "fugitive_width", precision = 6, scale = 0)
    private Long fugitiveWidth;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fugitive_width_uom_code", nullable = false)
    private UnitMeasureCode fugitiveWidthUomCode;
    
    @Column(name = "fugitive_length", precision = 6, scale = 0)
    private Long fugitiveLength;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fugitive_length_uom_code", nullable = false)
    private UnitMeasureCode fugitiveLengthUomCode;
    
    @Column(name = "fugitive_angle", precision = 3, scale = 0)
    private Long fugitiveAngle;
    
    @Column(name = "comments", length = 200)
    private String comments;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "releasePoint")
    private Set<ReleasePointAppt> releasePointAppts = new HashSet<ReleasePointAppt>(0);

    
    /***
     * Default constructor
     */
    public ReleasePoint() {}
    
    
    /***
     * Copy constructor (release point apportionment is not copied here, they are copied within the EmissionsProcess entity)
     * @param facilitySite The facility site object that this release point should be associated with
     * @param originalReleasePoint The release point object being copied
     */
    public ReleasePoint(FacilitySite facilitySite, ReleasePoint originalReleasePoint) {
		this.id = originalReleasePoint.getId();
        this.programSystemCode = originalReleasePoint.getProgramSystemCode();
        this.operatingStatusCode = originalReleasePoint.getOperatingStatusCode();
        this.facilitySite = facilitySite;
        this.releasePointIdentifier = originalReleasePoint.getReleasePointIdentifier();
        this.typeCode = originalReleasePoint.getTypeCode();
        this.description = originalReleasePoint.getDescription();
        this.stackHeight = originalReleasePoint.getStackHeight();
        this.stackHeightUomCode = originalReleasePoint.getStackHeightUomCode();
        this.stackDiameter = originalReleasePoint.getStackDiameter();
        this.stackDiameterUomCode = originalReleasePoint.getStackDiameterUomCode();
        this.exitGasVelocity = originalReleasePoint.getExitGasVelocity();
        this.exitGasVelocityUomCode = originalReleasePoint.getExitGasVelocityUomCode();
        this.exitGasTemperature = originalReleasePoint.getExitGasTemperature();
        this.exitGasFlowRate = originalReleasePoint.getExitGasFlowRate();
        this.exitGasFlowUomCode = originalReleasePoint.getExitGasFlowUomCode();
        this.statusYear = originalReleasePoint.getStatusYear();
        this.fugitiveLine1Latitude = originalReleasePoint.getFugitiveLine1Latitude();
        this.fugitiveLine1Longitude = originalReleasePoint.getFugitiveLine1Longitude();
        this.fugitiveLine2Latitude = originalReleasePoint.getFugitiveLine2Latitude();
        this.fugitiveLine2Longitude = originalReleasePoint.getFugitiveLine2Longitude();
        this.latitude = originalReleasePoint.getLatitude();
        this.longitude = originalReleasePoint.getLongitude();
        this.comments = originalReleasePoint.getComments();
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

    public FacilitySite getFacilitySite() {
        return this.facilitySite;
    }

    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public String getReleasePointIdentifier() {
        return this.releasePointIdentifier;
    }

    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public ReleasePointTypeCode getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(ReleasePointTypeCode typeCode) {
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

    public UnitMeasureCode getStackHeightUomCode() {
        return this.stackHeightUomCode;
    }

    public void setStackHeightUomCode(UnitMeasureCode stackHeightUomCode) {
        this.stackHeightUomCode = stackHeightUomCode;
    }

    public Double getStackDiameter() {
        return this.stackDiameter;
    }

    public void setStackDiameter(Double stackDiameter) {
        this.stackDiameter = stackDiameter;
    }

    public UnitMeasureCode getStackDiameterUomCode() {
        return this.stackDiameterUomCode;
    }

    public void setStackDiameterUomCode(UnitMeasureCode stackDiameterUomCode) {
        this.stackDiameterUomCode = stackDiameterUomCode;
    }

    public Double getExitGasVelocity() {
        return this.exitGasVelocity;
    }

    public void setExitGasVelocity(Double exitGasVelocity) {
        this.exitGasVelocity = exitGasVelocity;
    }

    public UnitMeasureCode getExitGasVelocityUomCode() {
        return this.exitGasVelocityUomCode;
    }

    public void setExitGasVelocityUomCode(UnitMeasureCode exitGasVelicityUomCode) {
        this.exitGasVelocityUomCode = exitGasVelicityUomCode;
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

    public UnitMeasureCode getExitGasFlowUomCode() {
        return this.exitGasFlowUomCode;
    }

    public void setExitGasFlowUomCode(UnitMeasureCode exitGasFlowUomCode) {
        this.exitGasFlowUomCode = exitGasFlowUomCode;
    }

    public Short getStatusYear() {
        return this.statusYear;
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

		public UnitMeasureCode getFenceLineUomCode() {
			return fenceLineUomCode;
		}

		public void setFenceLineUomCode(UnitMeasureCode fenceLineUomCode) {
			this.fenceLineUomCode = fenceLineUomCode;
		}

		public Long getFugitiveHeight() {
			return fugitiveHeight;
		}

		public void setFugitiveHeight(Long fugitiveHeight) {
			this.fugitiveHeight = fugitiveHeight;
		}

		public UnitMeasureCode getFugitiveHeightUomCode() {
			return fugitiveHeightUomCode;
		}

		public void setFugitiveHeightUomCode(UnitMeasureCode fugitiveHeightUomCode) {
			this.fugitiveHeightUomCode = fugitiveHeightUomCode;
		}

		public Long getFugitiveWidth() {
			return fugitiveWidth;
		}

		public void setFugitiveWidth(Long fugitiveWidth) {
			this.fugitiveWidth = fugitiveWidth;
		}

		public UnitMeasureCode getFugitiveWidthUomCode() {
			return fugitiveWidthUomCode;
		}

		public void setFugitiveWidthUomCode(UnitMeasureCode fugitiveWidthUomCode) {
			this.fugitiveWidthUomCode = fugitiveWidthUomCode;
		}

		public Long getFugitiveLength() {
			return fugitiveLength;
		}

		public void setFugitiveLength(Long fugitiveLength) {
			this.fugitiveLength = fugitiveLength;
		}

		public UnitMeasureCode getFugitiveLengthUomCode() {
			return fugitiveLengthUomCode;
		}

		public void setFugitiveLengthUomCode(UnitMeasureCode fugitiveLengthUomCode) {
			this.fugitiveLengthUomCode = fugitiveLengthUomCode;
		}

		public Long getFugitiveAngle() {
			return fugitiveAngle;
		}

		public void setFugitiveAngle(Long fugitiveAngle) {
			this.fugitiveAngle = fugitiveAngle;
		}

		public Set<ReleasePointAppt> getReleasePointAppts() {
        return this.releasePointAppts;
    }

    public void setReleasePointAppts(Set<ReleasePointAppt> releasePointAppts) {
        this.releasePointAppts = releasePointAppts;
    }
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}