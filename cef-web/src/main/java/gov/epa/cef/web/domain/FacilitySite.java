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
 * Facility entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "facility_site")

public class FacilitySite extends BaseAuditEntity {
    
    private static final long serialVersionUID = 1L;

    // Fields
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private FacilityCategoryCode facilityCategoryCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_type_code")
    private FacilitySourceTypeCode facilitySourceTypeCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_system_code", nullable = false)
    private ProgramSystemCode programSystemCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", nullable = false)
    private OperatingStatusCode operatingStatusCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private EmissionsReport emissionsReport;
    
    @Column(name = "frs_facility_id", length = 22)
    private String frsFacilityId;
    
    @Column(name = "eis_program_id", length = 22)
    private String eisProgramId;
    
    @Column(name = "alt_site_identifier", length = 30)
    private String altSiteIdentifier;
    
    @Column(name = "name", nullable = false, length = 80)
    private String name;
    
    @Column(name = "description", length = 100)
    private String description;
    
    @Column(name = "status_year", nullable = false)
    private Short statusYear;
    
    @Column(name = "street_address", nullable = false, length = 100)
    private String streetAddress;
    
    @Column(name = "city", nullable = false, length = 60)
    private String city;
    
    @Column(name = "county", length = 43)
    private String county;
    
    @Column(name = "state_code", nullable = false, length = 5)
    private String stateCode;
    
    @Column(name = "country_code", length = 10)
    private String countryCode;
    
    @Column(name = "postal_code", length = 10)
    private String postalCode;
    
    @Column(name = "mailing_street_address", length = 100)
    private String mailingStreetAddress;
    
    @Column(name = "mailing_city", length = 60)
    private String mailingCity;
    
    @Column(name = "mailing_state_code", length = 5)
    private String mailingStateCode;
    
    @Column(name = "mailing_postal_code", length = 10)
    private String mailingPostalCode;
    
    @Column(name = "latitude", precision = 10, scale = 6)
    private Double latitude;
    
    @Column(name = "longitude", precision = 10, scale = 6)
    private Double longitude;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribal_code", nullable = false)
    private TribalCode tribalCode;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private Set<FacilityNAICSXref> facilityNAICS = new HashSet<FacilityNAICSXref>(0);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private Set<EmissionsUnit> emissionsUnits = new HashSet<EmissionsUnit>(0);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private Set<ReleasePoint> releasePoints = new HashSet<ReleasePoint>(0);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private Set<FacilitySiteContact> contacts = new HashSet<FacilitySiteContact>(0);

    // Constructors

    /** default constructor */
    public FacilitySite() {
    }

    public FacilityCategoryCode getFacilityCategoryCode() {
        return this.facilityCategoryCode;
    }

    public void setFacilityCategoryCode(FacilityCategoryCode facilityCategoryCode) {
        this.facilityCategoryCode = facilityCategoryCode;
    }

    public FacilitySourceTypeCode getFacilitySourceTypeCode() {
        return this.facilitySourceTypeCode;
    }

    public void setFacilitySourceTypeCode(FacilitySourceTypeCode facilitySourceTypeCode) {
        this.facilitySourceTypeCode = facilitySourceTypeCode;
    }

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

    public EmissionsReport getEmissionsReport() {
        return this.emissionsReport;
    }

    public void setEmissionsReport(EmissionsReport emissionsReport) {
        this.emissionsReport = emissionsReport;
    }

    public String getFrsFacilityId() {
        return this.frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {
        this.frsFacilityId = frsFacilityId;
    }

    public String getEisProgramId() {
        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {
        this.eisProgramId = eisProgramId;
    }

    public String getAltSiteIdentifier() {
        return this.altSiteIdentifier;
    }

    public void setAltSiteIdentifier(String altSiteIdentifier) {
        this.altSiteIdentifier = altSiteIdentifier;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getStatusYear() {
        return this.statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMailingStreetAddress() {
        return mailingStreetAddress;
    }

    public void setMailingStreetAddress(String mailingStreetAddress) {
        this.mailingStreetAddress = mailingStreetAddress;
    }

    public String getMailingCity() {
        return mailingCity;
    }

    public void setMailingCity(String mailingCity) {
        this.mailingCity = mailingCity;
    }

    public String getMailingStateCode() {
        return mailingStateCode;
    }

    public void setMailingStateCode(String mailingStateCode) {
        this.mailingStateCode = mailingStateCode;
    }

    public String getMailingPostalCode() {
        return mailingPostalCode;
    }

    public void setMailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
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

    public TribalCode getTribalCode() {
        return tribalCode;
    }

    public void setTribalCode(TribalCode tribalCode) {
        this.tribalCode = tribalCode;
    }

    public Set<FacilityNAICSXref> getFacilityNAICS() {
        return facilityNAICS;
    }

    public void setFacilityNAICS(Set<FacilityNAICSXref> facilityNAICS) {
        this.facilityNAICS = facilityNAICS;
    }

    public Set<EmissionsUnit> getEmissionsUnits() {
        return this.emissionsUnits;
    }

    public void setEmissionsUnits(Set<EmissionsUnit> emissionsUnits) {
        this.emissionsUnits = emissionsUnits;
    }

    public Set<ReleasePoint> getReleasePoints() {
        return this.releasePoints;
    }

    public void setReleasePoints(Set<ReleasePoint> releasePoints) {
        this.releasePoints = releasePoints;
    }

    public Set<FacilitySiteContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<FacilitySiteContact> contacts) {
        this.contacts = contacts;
    }

}