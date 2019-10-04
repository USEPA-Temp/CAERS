package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Facility entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "facility_site")
public class FacilitySite extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private FacilityCategoryCode facilityCategoryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_type_code")
    private FacilitySourceTypeCode facilitySourceTypeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_system_code")
    private ProgramSystemCode programSystemCode;

    @NotNull
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

    @Column(name = "status_year")
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
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribal_code")
    private TribalCode tribalCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private List<FacilityNAICSXref> facilityNAICS = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private List<EmissionsUnit> emissionsUnits = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private List<ReleasePoint> releasePoints = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private List<FacilitySiteContact> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private List<Control> controls = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "facilitySite")
    private Set<ControlPath> controlPaths = new HashSet<ControlPath>(0);


    /***
     * Default constructor
     */
    public FacilitySite() {}


    /***
     * Copy constructor
     * @param emissionsReport The emissions report object that this facility site should be associated with
     * @param originalFacilitySite The facility site object being copied
     */
    public FacilitySite(EmissionsReport emissionsReport, FacilitySite originalFacilitySite) {
		this.id = originalFacilitySite.getId();
        this.facilityCategoryCode = originalFacilitySite.getFacilityCategoryCode();
        this.facilitySourceTypeCode = originalFacilitySite.getFacilitySourceTypeCode();
        this.programSystemCode = originalFacilitySite.getProgramSystemCode();
        this.operatingStatusCode = originalFacilitySite.getOperatingStatusCode();
        this.emissionsReport = emissionsReport;
        this.frsFacilityId = originalFacilitySite.getFrsFacilityId();
        this.eisProgramId = originalFacilitySite.getEisProgramId();
        this.altSiteIdentifier = originalFacilitySite.getAltSiteIdentifier();
        this.name = originalFacilitySite.getName();
        this.description = originalFacilitySite.getDescription();
        this.statusYear = originalFacilitySite.getStatusYear();
        this.streetAddress = originalFacilitySite.getStreetAddress();
        this.city = originalFacilitySite.getCity();
        this.county = originalFacilitySite.getCounty();
        this.stateCode = originalFacilitySite.getStateCode();
        this.countryCode = originalFacilitySite.getCountryCode();
        this.postalCode = originalFacilitySite.getPostalCode();
        this.mailingStreetAddress = originalFacilitySite.getMailingStreetAddress();
        this.mailingCity = originalFacilitySite.getMailingCity();
        this.mailingStateCode = originalFacilitySite.getMailingStateCode();
        this.mailingPostalCode = originalFacilitySite.getMailingPostalCode();
        this.latitude = originalFacilitySite.getLatitude();
        this.longitude = originalFacilitySite.getLongitude();
        this.tribalCode = originalFacilitySite.getTribalCode();

        for (FacilityNAICSXref naicsXref : originalFacilitySite.getFacilityNAICS()) {
        	this.facilityNAICS.add(new FacilityNAICSXref(this, naicsXref));
        }
        for (ReleasePoint releasePoint : originalFacilitySite.getReleasePoints()) {
        	this.releasePoints.add(new ReleasePoint(this, releasePoint));
        }
        for (EmissionsUnit emissionsUnit : originalFacilitySite.getEmissionsUnits()) {
        	this.emissionsUnits.add(new EmissionsUnit(this, emissionsUnit));
        }
        for (FacilitySiteContact siteContact : originalFacilitySite.getContacts()) {
        	this.contacts.add(new FacilitySiteContact(this, siteContact));
        }
        for (Control control : originalFacilitySite.getControls()) {
        	this.controls.add(new Control(this, control));
        }
        for (ControlPath controlPath : originalFacilitySite.getControlPaths()) {
        	this.controlPaths.add(new ControlPath(this, controlPath));
        }
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

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public TribalCode getTribalCode() {
        return tribalCode;
    }

    public void setTribalCode(TribalCode tribalCode) {
        this.tribalCode = tribalCode;
    }

    public List<FacilityNAICSXref> getFacilityNAICS() {
        return facilityNAICS;
    }

    public void setFacilityNAICS(List<FacilityNAICSXref> facilityNAICS) {

        this.facilityNAICS.clear();
        if (facilityNAICS != null) {
            this.facilityNAICS.addAll(facilityNAICS);
        }
    }

    public List<EmissionsUnit> getEmissionsUnits() {
        return this.emissionsUnits;
    }

    public void setEmissionsUnits(List<EmissionsUnit> emissionsUnits) {

        this.emissionsUnits.clear();
        if (emissionsUnits != null) {
            this.emissionsUnits.addAll(emissionsUnits);
        }
    }

    public List<ReleasePoint> getReleasePoints() {
        return this.releasePoints;
    }

    public void setReleasePoints(List<ReleasePoint> releasePoints) {

        this.releasePoints.clear();
        if (releasePoints != null) {
            this.releasePoints.addAll(releasePoints);
        };
    }

    public List<FacilitySiteContact> getContacts() {
        return this.contacts;
    }

    public void setContacts(List<FacilitySiteContact> contacts) {

        this.contacts.clear();
        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    public List<Control> getControls() {
        return controls;
    }

    public void setControls(List<Control> controls) {
        this.controls.clear();
        if (controls != null) {
            this.controls.addAll(controls);
        }
    }

    public Set<ControlPath> getControlPaths() {
        return controlPaths;
    }

    public void setControlPaths(Set<ControlPath> controlPaths) {
        this.controlPaths = controlPaths;
    }


    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;

        for (FacilityNAICSXref naicsXref : this.facilityNAICS) {
        	naicsXref.clearId();
        }
		for (ReleasePoint releasePoint : this.releasePoints) {
			releasePoint.clearId();
		}
		for (EmissionsUnit emissionsUnit : this.emissionsUnits) {
			emissionsUnit.clearId();
		}
        for (FacilitySiteContact contact : this.contacts) {
        	contact.clearId();
        }
		for (Control control : this.controls) {
			control.clearId();
		}
		for (ControlPath controlPath : this.controlPaths) {
			controlPath.clearId();
		}
    }
}
