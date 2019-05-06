package gov.epa.cef.web.domain.common;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class BaseAuditEntity extends BaseEntity {
	
	@CreatedDate
	protected Date createdDate;
	
	@LastModifiedDate
	protected Date lastModifiedDate;
	
	//TODO: add created by and last updated by after user table has been created

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
