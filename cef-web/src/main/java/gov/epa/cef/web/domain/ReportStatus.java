package gov.epa.cef.web.domain;

public enum ReportStatus {
	NEW("New"),
    APPROVED("Approved"),
    IN_PROGRESS("In Progress"),
    SUBMITTED("Submitted"),
    VALIDATED("Validated");

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
