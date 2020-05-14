package gov.epa.cef.web.service.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EisHeaderDto {

    private EisDataCategory dataCategory;

    private EisSubmissionType submissionType;

    private final Set<Long> emissionReports;

    public EisHeaderDto() {

        this.emissionReports = new HashSet<>();
    }

    public Set<Long> getEmissionReports() {

        return emissionReports;
    }

    public void setEmissionReports(Collection<Long> emissionReports) {

        this.emissionReports.clear();
        if (emissionReports != null) {
            this.emissionReports.addAll(emissionReports);
        }
    }

    public EisDataCategory getDataCategory() {

        return dataCategory;
    }

    public void setDataCategory(EisDataCategory dataCategory) {

        this.dataCategory = dataCategory;
    }

    public EisSubmissionType getSubmissionType() {

        return submissionType;
    }

    public void setSubmissionType(EisSubmissionType submissionType) {

        this.submissionType = submissionType;
    }
}
