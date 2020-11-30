package gov.epa.cef.web.service.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EisHeaderDto {

    private final Set<Long> emissionsReports;

    private String authorName;

    private String organizationName;

    private String programSystemCode;

    private EisSubmissionStatus submissionStatus;

    public EisHeaderDto() {

        this.emissionsReports = new HashSet<>();
    }

    public String getAuthorName() {

        return authorName;
    }

    public void setAuthorName(String authorName) {

        this.authorName = authorName;
    }

    public Set<Long> getEmissionsReports() {

        return emissionsReports;
    }

    public void setEmissionsReports(Collection<Long> emissionsReports) {

        this.emissionsReports.clear();
        if (emissionsReports != null) {
            this.emissionsReports.addAll(emissionsReports);
        }
    }

    public String getOrganizationName() {

        return organizationName;
    }

    public void setOrganizationName(String organizationName) {

        this.organizationName = organizationName;
    }

    public String getProgramSystemCode() {

        return programSystemCode;
    }

    public void setProgramSystemCode(String programSystemCode) {

        this.programSystemCode = programSystemCode;
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

    public EisHeaderDto withEmissionsReports(Collection<Long> ids) {

        setEmissionsReports(ids);
        return this;
    }

    public EisHeaderDto withOrganizationName(final String organizationName) {

        setOrganizationName(organizationName);
        return this;
    }

    public EisHeaderDto withProgramSystemCode(final String programSystemCode) {

        setProgramSystemCode(programSystemCode);
        return this;
    }

    public EisHeaderDto withSubmissionStatus(final EisSubmissionStatus submissionStatus) {

        setSubmissionStatus(submissionStatus);
        return this;
    }

    public interface EisApiGroup { /* marker */

    }
}
