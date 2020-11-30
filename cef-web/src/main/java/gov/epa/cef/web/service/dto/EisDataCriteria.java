package gov.epa.cef.web.service.dto;

public class EisDataCriteria {

    private String programSystemCode;

    private short reportingYear;

    private EisSubmissionStatus submissionStatus;

    public String getProgramSystemCode() {

        return programSystemCode;
    }

    public void setProgramSystemCode(String programSystemCode) {

        this.programSystemCode = programSystemCode;
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

    public EisDataCriteria withProgramSystemCode(final String programSystemCode) {

        setProgramSystemCode(programSystemCode);
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
