package gov.epa.cef.web.domain.report;

public enum ReportStatus {
    CERTIFIED("Certified"), 
    FAILED("Failed Validation"), 
    IN_PROGRESS("In Progress"),
    NOT_STARTED("Not Started"),
    PASSED("Passed Validaton"),
    PASSED_ALERTS("Passed with Alerts"),
    PENDING("Pending");

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
