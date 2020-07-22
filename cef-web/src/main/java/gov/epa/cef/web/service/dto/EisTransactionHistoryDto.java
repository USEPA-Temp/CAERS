package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.Date;

public class EisTransactionHistoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String agencyCode;
    private Date createdDate;
    private EisSubmissionStatus eisSubmissionStatus;
    private String transactionId;
    private String submitterName;
    private EisTransactionAttachmentDto attachment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public EisSubmissionStatus getEisSubmissionStatus() {
        return eisSubmissionStatus;
    }

    public void setEisSubmissionStatus(EisSubmissionStatus eisSubmissionStatus) {
        this.eisSubmissionStatus = eisSubmissionStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public EisTransactionAttachmentDto getAttachment() {
        return attachment;
    }

    public void setAttachment(EisTransactionAttachmentDto attachment) {
        this.attachment = attachment;
    }
}
