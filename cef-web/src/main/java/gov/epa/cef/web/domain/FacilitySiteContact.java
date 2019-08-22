package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "facility_site_contact")
public class FacilitySiteContact extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_site_id", nullable = false)
    private FacilitySite facilitySite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", nullable = false)
    private ContactTypeCode type;

    @Column(name = "prefix", length = 15)
    private String prefix;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "phone_ext", length = 5)
    private String phoneExt;

    @Column(name = "street_address", nullable = false, length = 100)
    private String streetAddress;

    @Column(name = "city", nullable = false, length = 60)
    private String city;

    @Column(name = "state_code", nullable = false, length = 5)
    private String stateCode;

    @Column(name = "country_code", length = 10)
    private String countryCode;

    @Column(name = "postal_code", length = 10)
    private String postalCode;
    
    @Column(name = "county", length = 43)
    private String county;

    @Column(name = "mailing_street_address", length = 100)
    private String mailingStreetAddress;

    @Column(name = "mailing_city", length = 60)
    private String mailingCity;

    @Column(name = "mailing_state_code", length = 5)
    private String mailingStateCode;

    @Column(name = "mailing_postal_code", length = 10)
    private String mailingPostalCode;

    /**
     * Default constructor
     */
    public FacilitySiteContact() {}
    
    
    /**
     * Copy constructor
     */
    public FacilitySiteContact(FacilitySite facilitySite, FacilitySiteContact originalFacilitySiteContact) {
    	this.id = originalFacilitySiteContact.getId();
    	this.facilitySite = facilitySite;
    	this.type = originalFacilitySiteContact.getType();
    	this.prefix = originalFacilitySiteContact.getPrefix();
    	this.firstName = originalFacilitySiteContact.getFirstName();
    	this.lastName = originalFacilitySiteContact.getLastName();
    	this.email = originalFacilitySiteContact.getEmail();
    	this.phone = originalFacilitySiteContact.getPhone();
    	this.phoneExt = originalFacilitySiteContact.getPhoneExt();
    	this.streetAddress = originalFacilitySiteContact.getStreetAddress();
    	this.city = originalFacilitySiteContact.getCity();
    	this.stateCode = originalFacilitySiteContact.getStateCode();
    	this.countryCode = originalFacilitySiteContact.getCountryCode();
    	this.postalCode = originalFacilitySiteContact.getPostalCode();
    	this.county = originalFacilitySiteContact.getCounty();
    	this.mailingStreetAddress = originalFacilitySiteContact.getMailingStreetAddress();
    	this.mailingCity = originalFacilitySiteContact.getMailingCity();
    	this.mailingStateCode = originalFacilitySiteContact.getMailingStateCode();
    	this.mailingPostalCode = originalFacilitySiteContact.getMailingPostalCode();
    }
    
    
    
    public FacilitySite getFacilitySite() {
        return facilitySite;
    }

    public void setFacilitySite(FacilitySite facilitySite) {
        this.facilitySite = facilitySite;
    }

    public ContactTypeCode getType() {
        return type;
    }

    public void setType(ContactTypeCode type) {
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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
    
    
    /***
     * Set the id property to null for this object and the id for it's direct children.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
