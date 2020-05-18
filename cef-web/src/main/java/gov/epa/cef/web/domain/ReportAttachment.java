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

	@Column(name = "file_name", length = 50)
	private String fileName;
	
	@Lob
	@Column(name = "attachment")
	private Blob attachment;
	
	@Column(name = "file_type", length = 1000)
	private String fileType;
	
	/***
     * Default constructor
     */
    public ReportAttachment() {}
    
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

}
