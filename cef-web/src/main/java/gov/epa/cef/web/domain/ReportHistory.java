/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
	
	@Column(name = "user_role")
	private String userRole;

	@Column(name = "comments", length = 2000)
	private String comments;
	
	@Column(name = "attachment_id")
	private Long reportAttachmentId;
	
	@Column(name = "file_name", length = 255)
	private String fileName;
	
	@Column(name = "file_deleted")
	private boolean fileDeleted;
	
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

	public Long getReportAttachmentId() {
		return reportAttachmentId;
	}

	public void setReportAttachmentId(Long reportAttachmentId) {
		this.reportAttachmentId = reportAttachmentId;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isFileDeleted() {
		return fileDeleted;
	}

	public void setFileDeleted(boolean fileDeleted) {
		this.fileDeleted = fileDeleted;
	}

}
