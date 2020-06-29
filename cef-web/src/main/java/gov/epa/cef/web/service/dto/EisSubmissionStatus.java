package gov.epa.cef.web.service.dto;

public enum EisSubmissionStatus {

	NotStarted("Not Started", "NotStarted"),
	QaEmissions("QA", "PointEmission"),
    QaFacility("QA", "FacilityInventory"),
    ProdEmissions("Production", "PointEmission"),
    ProdFacility("Production", "FacilityInventory"),
    Complete("Complete", "Complete");

    private final String submissionType;

    private final String dataCategory;

    EisSubmissionStatus(String submissionType, String dataCategory) {

        this.submissionType = submissionType;
        this.dataCategory = dataCategory;
    }

    public String submissionType() {

        return this.submissionType;
    }

    public String dataCategory() {

        return this.dataCategory;
    }
}
