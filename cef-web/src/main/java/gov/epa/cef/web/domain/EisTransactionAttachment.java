package gov.epa.cef.web.domain;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * ReportAttachments entity
 */
@Entity
@Table(name = "eis_transaction_attachment")
public class EisTransactionAttachment extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eis_transaction_history_id")
    private EisTransactionHistory transactionHistory;

	@Column(name = "file_name", length = 255)
	private String fileName;

	@Lob
	@Column(name = "attachment")
	private Blob attachment;

	@Column(name = "file_type", length = 1000)
	private String fileType;

	/***
     * Default constructor
     */
    public EisTransactionAttachment() {}

	public EisTransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(EisTransactionHistory transaction) {
        this.transactionHistory = transaction;
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

	/***
     * Set the id property to null for this object.  This method is useful to INSERT the updated object instead of UPDATE.
     */
    public void clearId() {
    	this.id = null;
    }

}
