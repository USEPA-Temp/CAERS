package gov.epa.cef.web.domain;

public enum ValidationStatus {
    FAILED("Failed Validation"), 
    PASSED("Passed Validaton"),
    PASSED_WARNINGS("Passed Validaton with Warnings"),
    UNVALIDATED("Not Validated");

    private final String label;

    ValidationStatus(String label) {
        this.label = label;
    }

    public String code() {
        return this.name();
    }

    public String label() {
        return this.label;
    }

}
