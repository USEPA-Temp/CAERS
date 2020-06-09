package gov.epa.cef.web.service.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EisHeaderDto {

    private final Set<Long> emissionReports;

    private String authorName;

    private String organizationName;

    private EisSubmissionStatus submissionStatus;

    public EisHeaderDto() {

        this.emissionReports = new HashSet<>();
    }

    public String getAuthorName() {

        return authorName;
    }

    public void setAuthorName(String authorName) {

        this.authorName = authorName;
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

    public String getOrganizationName() {

        return organizationName;
    }

    public void setOrganizationName(String organizationName) {

        this.organizationName = organizationName;
    }

    public EisSubmissionStatus getSubmissionStatus() {

        return submissionStatus;
    }

    public void setSubmissionStatus(EisSubmissionStatus submissionStatus) {

        this.submissionStatus = submissionStatus;
    }

    public EisHeaderDto withAuthorName(final String authorName) {

        setAuthorName(authorName);
        return this;
    }

    public EisHeaderDto withEmissionReports(Collection<Long> ids) {

        setEmissionReports(ids);
        return this;
    }

    public EisHeaderDto withOrganizationName(final String organizationName) {

        setOrganizationName(organizationName);
        return this;
    }

    public EisHeaderDto withSubmissionStatus(final EisSubmissionStatus submissionStatus) {

        setSubmissionStatus(submissionStatus);
        return this;
    }
}
