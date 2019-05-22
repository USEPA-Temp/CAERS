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
 * Facility entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "facility")

public class Facility implements java.io.Serializable {

    // Fields

    private Long id;
    private FacilityCategoryCode facilityCategoryCode;
    private FacilitySourceTypeCode facilitySourceTypeCode;
    private NaicsCode naicsCode;
    private ProgramSystemCode programSystemCode;
    private OperatingStatusCode operatingStatusCode;
    private EmissionsReport emissionsReport;
    private String frsFacilityId;
    private String altSiteIdentifier;
    private String name;
    private String description;
    private Short statusYear;
    private String streetAddress;
    private String city;
    private String county;
    private String stateCode;
    private String countryCode;
    private String postalCode;
    private Double latitude;
    private Double longitude;
    private String createdBy;
    private Timestamp createdDate;
    private String lastModifiedBy;
    private Timestamp lastModifiedDate;
    private Set<EmissionsUnit> emissionsUnits = new HashSet<EmissionsUnit>(0);
    private Set<ReleasePoint> releasePoints = new HashSet<ReleasePoint>(0);

    // Constructors

    /** default constructor */
    public Facility() {
    }

    /** minimal constructor */
    public Facility(Long id, NaicsCode naicsCode, ProgramSystemCode programSystemCode,
            OperatingStatusCode operatingStatusCode, EmissionsReport emissionsReport, String name, Short statusYear,
            String streetAddress, String city, String stateCode, String createdBy, Timestamp createdDate,
            String lastModifiedBy, Timestamp lastModifiedDate) {
        this.id = id;
        this.naicsCode = naicsCode;
        this.programSystemCode = programSystemCode;
        this.operatingStatusCode = operatingStatusCode;
        this.emissionsReport = emissionsReport;
        this.name = name;
        this.statusYear = statusYear;
        this.streetAddress = streetAddress;
        this.city = city;
        this.stateCode = stateCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /** full constructor */
    public Facility(Long id, FacilityCategoryCode facilityCategoryCode, FacilitySourceTypeCode facilitySourceTypeCode,
            NaicsCode naicsCode, ProgramSystemCode programSystemCode, OperatingStatusCode operatingStatusCode,
            EmissionsReport emissionsReport, String frsFacilityId, String altSiteIdentifier, String name,
            String description, Short statusYear, String streetAddress, String city, String county, String stateCode,
            String countryCode, String postalCode, Double latitude, Double longitude, String createdBy,
            Timestamp createdDate, String lastModifiedBy, Timestamp lastModifiedDate, Set<EmissionsUnit> emissionsUnits,
            Set<ReleasePoint> releasePoints) {
        this.id = id;
        this.facilityCategoryCode = facilityCategoryCode;
        this.facilitySourceTypeCode = facilitySourceTypeCode;
        this.naicsCode = naicsCode;
        this.programSystemCode = programSystemCode;
        this.operatingStatusCode = operatingStatusCode;
        this.emissionsReport = emissionsReport;
        this.frsFacilityId = frsFacilityId;
        this.altSiteIdentifier = altSiteIdentifier;
        this.name = name;
        this.description = description;
        this.statusYear = statusYear;
        this.streetAddress = streetAddress;
        this.city = city;
        this.county = county;
        this.stateCode = stateCode;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.emissionsUnits = emissionsUnits;
        this.releasePoints = releasePoints;
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
    @JoinColumn(name = "category_code")

    public FacilityCategoryCode getFacilityCategoryCode() {
        return this.facilityCategoryCode;
    }

    public void setFacilityCategoryCode(FacilityCategoryCode facilityCategoryCode) {
        this.facilityCategoryCode = facilityCategoryCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_type_code")

    public FacilitySourceTypeCode getFacilitySourceTypeCode() {
        return this.facilitySourceTypeCode;
    }

    public void setFacilitySourceTypeCode(FacilitySourceTypeCode facilitySourceTypeCode) {
        this.facilitySourceTypeCode = facilitySourceTypeCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "naics_code", nullable = false)

    public NaicsCode getNaicsCode() {
        return this.naicsCode;
    }

    public void setNaicsCode(NaicsCode naicsCode) {
        this.naicsCode = naicsCode;
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
    @JoinColumn(name = "report_id", nullable = false)

    public EmissionsReport getEmissionsReport() {
        return this.emissionsReport;
    }

    public void setEmissionsReport(EmissionsReport emissionsReport) {
        this.emissionsReport = emissionsReport;
    }

    @Column(name = "frs_facility_id", length = 22)

    public String getFrsFacilityId() {
        return this.frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    @Column(name = "alt_site_identifier", length = 30)

    public String getAltSiteIdentifier() {
        return this.altSiteIdentifier;
    }

    public void setAltSiteIdentifier(String altSiteIdentifier) {
        this.altSiteIdentifier = altSiteIdentifier;
    }

    @Column(name = "name", nullable = false, length = 80)

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 100)

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "status_year", nullable = false)

    public Short getStatusYear() {
        return this.statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    @Column(name = "street_address", nullable = false, length = 100)

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Column(name = "city", nullable = false, length = 60)

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "county", length = 43)

    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name = "state_code", nullable = false, length = 5)

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Column(name = "country_code", length = 10)

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "postal_code", length = 10)

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "latitude", precision = 10, scale = 6)

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", precision = 10, scale = 6)

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facility")

    public Set<EmissionsUnit> getEmissionsUnits() {
        return this.emissionsUnits;
    }

    public void setEmissionsUnits(Set<EmissionsUnit> emissionsUnits) {
        this.emissionsUnits = emissionsUnits;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facility")

    public Set<ReleasePoint> getReleasePoints() {
        return this.releasePoints;
    }

    public void setReleasePoints(Set<ReleasePoint> releasePoints) {
        this.releasePoints = releasePoints;
    }

}