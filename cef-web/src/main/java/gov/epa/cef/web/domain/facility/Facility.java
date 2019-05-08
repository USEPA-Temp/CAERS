package gov.epa.cef.web.domain.facility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "facility")
public class Facility extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "state")
	private String state;
	
	@Column(name = "county")
	private String county;
	
	@Column(name = "naics")
	private String naics;
	
	@Column(name = "eisId")
	private Long eisId;
	
	@Column(name = "currentTons")
	private Long currentTons;
	
	@Column(name = "previousTons")
	private Long previousTons;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getNaics() {
		return naics;
	}
	public void setNaics(String naics) {
		this.naics = naics;
	}
	
	public Long getEisId() {
		return eisId;
	}
	public void setEisId(Long eisId) {
		this.eisId = eisId;
	}
	
	public Long getCurrentTons() {
		return currentTons;
	}
	public void setCurrentTons(Long currentTons) {
		this.currentTons = currentTons;
	}
	
	public Long getPreviousTons() {
		return previousTons;
	}
	public void setPreviousTons(Long previousTons) {
		this.previousTons = previousTons;
	}
	

	@Override
	public String toString() {
		return String.format("Facility [id=%f, name=%s, state=%s, county=%s, naics=%s, eisId=%f, currentTons=%f, previousTons=%f, createdBy=%s, createdDate=%s, lastModifiedBy=%s, lastModifiedDate=%s]", 
				id, name, state, county, naics, eisId, currentTons, previousTons, createdBy, createdDate.toString(), lastModifiedBy, lastModifiedDate.toString());
	}

}
