package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.Date;

public class ReportHistoryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long emissionsReportId;
	private String userId;
	private String userFullName;
	private Date actionDate;
	private String reportAction;
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getReportAction() {
		return reportAction;
	}

	public void setReportAction(String reportAction) {
		this.reportAction = reportAction;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getEmissionsReportId() {
		return emissionsReportId;
	}

	public void setEmissionsReportId(Long emissionsReportId) {
		this.emissionsReportId = emissionsReportId;
	}
}
