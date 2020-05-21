package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import gov.epa.cef.web.util.TempFile;

public class ReportAttachmentDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long reportId;
	private String fileName;
	private TempFile attachment;
	private String fileType;
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public TempFile getAttachment() {
		return attachment;
	}

	public void setAttachment(TempFile attachment) {
		this.attachment = attachment;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
