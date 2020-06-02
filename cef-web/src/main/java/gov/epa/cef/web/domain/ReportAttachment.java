package gov.epa.cef.web.domain;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * ReportAttachments entity
 */
@Entity
@Table(name = "report_attachment")
public class ReportAttachment extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private EmissionsReport emissionsReport;

	@Column(name = "file_name", length = 255)
	private String fileName;
	
	@Lob
	@Column(name = "attachment")
	private Blob attachment;
	
	@Column(name = "file_type", length = 1000)
	private String fileType;
	
	@Column(name = "comments", length = 2000)
	private String comments;
	
	/***
     * Default constructor
     */
    public ReportAttachment() {}
    
    /***
     * Copy constructor
     * 
     */
    public ReportAttachment(ReportAttachment originalAttachment) {
    	this.fileName = originalAttachment.fileName;
    	this.attachment = originalAttachment.attachment;
    	this.fileType = originalAttachment.fileType;
    	this.comments = originalAttachment.comments;
    }
    
	public EmissionsReport getEmissionsReport() {
		return emissionsReport;
	}

	public void setEmissionsReport(EmissionsReport emissionsReport) {
		this.emissionsReport = emissionsReport;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/***
     * Set the id property to null for this object.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
