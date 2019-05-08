package gov.epa.cef.web.domain.report;

public enum ReportStatus {

	Certified("Certified"), 
	Failed("Failed Validation"), 
	InProgress("In Progress"),
	NotStarted("Not Started"),
	Passed("Passed Validaton"),
	PassedAlerts("Passed with Alerts"),
	Pending("Pending");

	private final String label;

	ReportStatus(String label) {

		this.label = label;
	}

	public String code() {

		return this.name();
	}

	public String label() {

		return this.label;
	}
}
