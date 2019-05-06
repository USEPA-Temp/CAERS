package gov.epa.cef.web.domain.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "emissions_report")
public class EmissionsReport extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "facility_id")
	private String facilityId;
	
	@NotNull
	@Column(name = "status")
    @Enumerated(EnumType.STRING)
	private ReportStatus status;
	
	@NotNull
	@Column(name = "year")
	private Integer year;

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public ReportStatus getStatus() {
		return status;
	}

	public void setStatus(ReportStatus status) {
		this.status = status;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "EmissionsReport [facilityId=" + facilityId + ", status=" + status + ", year=" + year + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModifiedDate=" + lastModifiedDate + ", id=" + id + "]";
	}

}
