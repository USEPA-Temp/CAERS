package gov.epa.cef.web.domain.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class BaseAuditEntity extends BaseEntity {

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, length = 29)
    protected Date createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false, length = 29)
    protected Date lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
