package gov.epa.cef.web.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;

@Entity
@Table(name = "eis_transaction_history")
public class EisTransactionHistory extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_system_code")
    private ProgramSystemCode programSystemCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "eis_sub_status", nullable = false)
    private EisSubmissionStatus eisSubmissionStatus;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "submitter_name", nullable = false)
    private String submitterName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "transactionHistory")
    private EisTransactionAttachment attachment;

    public ProgramSystemCode getProgramSystemCode() {
        return programSystemCode;
    }

    public void setProgramSystemCode(ProgramSystemCode programSystemCode) {
        this.programSystemCode = programSystemCode;
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

    public EisTransactionAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(EisTransactionAttachment attachment) {
        this.attachment = attachment;
    }

}
