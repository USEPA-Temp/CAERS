package gov.epa.cef.web.domain;

public enum ReportAction {
	CREATED("Created New"),
	COPIED_FWD("Copied Forward"),
	UPLOADED("Report Uploaded"),
	REOPENED("Reopened"), 
	ACCEPTED("Accepted"), 
	REJECTED("Rejected"), 
	SUBMITTED("Submitted");

	private final String label;

	ReportAction(String label) {
		this.label = label;
	}

	public String code() {
		return this.name();
	}

	public String label() {
		return this.label;
	}
}
