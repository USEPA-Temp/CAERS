package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import gov.epa.cef.web.util.TempFile;

public class EisTransactionAttachmentDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long transactionHistoryId;
	private String fileName;
	private TempFile attachment;
	private String fileType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionHistoryId() {
        return transactionHistoryId;
    }

    public void setTransactionHistoryId(Long transactionHistoryId) {
        this.transactionHistoryId = transactionHistoryId;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EisHistoryAttachmentDto [id=").append(id).append(", transactionHistoryId=")
                .append(transactionHistoryId).append(", fileName=").append(fileName).append(", attachment=")
                .append(attachment).append(", fileType=").append(fileType).append("]");
        return builder.toString();
    }

}
