package gov.epa.cef.web.domain;

public enum AttachmentMIMEType {
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XLSX_CSV("application/vnd.ms-excel"),
    TXT("text/plain"),
    PDF("application/pdf"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    CSV("text/csv");
	
	private final String label;

	AttachmentMIMEType(String label) {
        this.label = label;
    }

    public String code() {
        return this.name();
    }

    public String label() {
        return this.label;
    }
}
