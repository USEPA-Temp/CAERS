package gov.epa.cef.web.service.dto;

public class EisDataCriteria {

    private String agencyCode;

    private short reportingYear;

    private EisSubmissionStatus submissionStatus;

    public String getAgencyCode() {

        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {

        this.agencyCode = agencyCode;
    }

    public short getReportingYear() {

        return reportingYear;
    }

    public void setReportingYear(int reportingYear) {

        this.reportingYear = new Integer(reportingYear).shortValue();
    }

    public EisSubmissionStatus getSubmissionStatus() {

        return submissionStatus;
    }

    public void setSubmissionStatus(EisSubmissionStatus submissionStatus) {

        this.submissionStatus = submissionStatus;
    }

    public EisDataCriteria withAgencyCode(final String agencyCode) {

        setAgencyCode(agencyCode);
        return this;
    }

    public EisDataCriteria withReportingYear(final int reportingYear) {

        setReportingYear(reportingYear);
        return this;
    }

    public EisDataCriteria withSubmissionStatus(final EisSubmissionStatus submissionStatus) {

        setSubmissionStatus(submissionStatus);
        return this;
    }
}
