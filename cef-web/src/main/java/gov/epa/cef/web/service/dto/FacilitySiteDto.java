package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.Set;

public class FacilitySiteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FacilityCategoryCodeDto facilityCategoryCode;
    private CodeLookupDto facilitySourceTypeCode;
    private CodeLookupDto programSystemCode;
    private CodeLookupDto operatingStatusCode;
    private CodeLookupDto tribalCode;
    private EmissionsReportDto emissionsReport;
    private String frsFacilityId;
    private String eisProgramId;
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
    private String mailingStreetAddress;
    private String mailingCity;
    private String mailingStateCode;
    private String mailingPostalCode;
    private Double latitude;
    private Double longitude;
    private String tribalCodeDesc;
    private Set<FacilityNAICSDto> facilityNAICS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FacilityCategoryCodeDto getFacilityCategoryCode() {
        return facilityCategoryCode;
    }

    public void setFacilityCategoryCode(FacilityCategoryCodeDto facilityCategoryCode) {
        this.facilityCategoryCode = facilityCategoryCode;
    }

    public CodeLookupDto getFacilitySourceTypeCode() {
        return facilitySourceTypeCode;
    }

    public void setFacilitySourceTypeCode(CodeLookupDto facilitySourceTypeCode) {
        this.facilitySourceTypeCode = facilitySourceTypeCode;
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

    public CodeLookupDto getTribalCode() {
        return tribalCode;
    }

    public void setTribalCode(CodeLookupDto tribalCode) {
        this.tribalCode = tribalCode;
    }

    public EmissionsReportDto getEmissionsReport() {
        return emissionsReport;
    }

    public void setEmissionsReport(EmissionsReportDto emissionsReport) {
        this.emissionsReport = emissionsReport;
    }

    public String getFrsFacilityId() {
        return frsFacilityId;
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
        return altSiteIdentifier;
    }

    public void setAltSiteIdentifier(String altSiteIdentifier) {
        this.altSiteIdentifier = altSiteIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getStatusYear() {
        return statusYear;
    }

    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getTribalCodeDesc() {
        return tribalCodeDesc;
    }

    public void setTribalCodeDesc(String tribalCodeDesc) {
        this.tribalCodeDesc = tribalCodeDesc;
    }

    public Set<FacilityNAICSDto> getFacilityNAICS() {
        return facilityNAICS;
    }

    public void setFacilityNAICS(Set<FacilityNAICSDto> facilityNAICS) {
        this.facilityNAICS = facilityNAICS;
    }

}
