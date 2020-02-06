package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class FacilitySiteContactBulkUploadDto implements IWorksheetAware, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long facilitySiteId;
	private String type;
	private String prefix;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String phoneExt;
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
	private String mailingCountryCode;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public String getMailingCountryCode() {
		return mailingCountryCode;
	}

	public void setMailingCountryCode(String mailingCountryCode) {
		this.mailingCountryCode = mailingCountryCode;
	}

}
