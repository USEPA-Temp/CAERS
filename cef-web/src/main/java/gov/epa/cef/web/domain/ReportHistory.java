package gov.epa.cef.web.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

/**
 * ReportHistory entity
 */
@Entity
@Table(name = "report_history")
public class ReportHistory extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_id", nullable = false)
	private EmissionsReport emissionsReport;

	@Enumerated(EnumType.STRING)
	@Column(name = "action", nullable = false)
	private ReportAction reportAction;

	@CreatedDate
	@Column(name = "action_date", nullable = false)
	protected Date actionDate;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "user_full_name", nullable = false)
	private String userFullName;

	@Column(name = "comments", length = 2000)
	private String comments;

	/**
	 * Default constructor
	 */
	public ReportHistory() {
	}

	public EmissionsReport getEmissionsReport() {
		return emissionsReport;
	}

	public void setEmissionsReport(EmissionsReport emissionsReport) {
		this.emissionsReport = emissionsReport;
	}

	public ReportAction getReportAction() {
		return reportAction;
	}

	public void setReportAction(ReportAction reportAction) {
		this.reportAction = reportAction;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
