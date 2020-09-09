package gov.epa.cef.web.service.dto;

import gov.epa.cef.web.util.ConstantUtils;

public enum EisSubmissionStatus {

	NotStarted("Not Started", "NotStarted"),
	QaEmissions("QA", ConstantUtils.EIS_TRANSMISSION_POINT_EMISSIONS),
    QaFacility("QA", ConstantUtils.EIS_TRANSMISSION_FACILITY_INVENTORY),
    ProdEmissions("Production", ConstantUtils.EIS_TRANSMISSION_POINT_EMISSIONS),
    ProdFacility("Production", ConstantUtils.EIS_TRANSMISSION_FACILITY_INVENTORY),
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
