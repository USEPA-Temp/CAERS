package gov.epa.cef.web.service.dto;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;

import java.util.function.Function;

public class EisDataReportDto {

    private String agencyFacilityId;

    private String comments;

    private String eisProgramId;

    private String facilityName;

    private EisSubmissionStatus lastSubmissionStatus;

    private String lastTransactionId;

    private boolean passed;

    private int reportingYear;

    public String getAgencyFacilityId() {

        return agencyFacilityId;
    }

    public void setAgencyFacilityId(String agencyFacilityId) {

        this.agencyFacilityId = agencyFacilityId;
    }

    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

    public String getEisProgramId() {

        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {

        this.eisProgramId = eisProgramId;
    }

    public String getFacilityName() {

        return facilityName;
    }

    public void setFacilityName(String facilityName) {

        this.facilityName = facilityName;
    }

    public EisSubmissionStatus getLastSubmissionStatus() {

        return lastSubmissionStatus;
    }

    public void setLastSubmissionStatus(EisSubmissionStatus lastSubmissionStatus) {

        this.lastSubmissionStatus = lastSubmissionStatus;
    }

    public String getLastTransactionId() {

        return lastTransactionId;
    }

    public void setLastTransactionId(String lastTransactionId) {

        this.lastTransactionId = lastTransactionId;
    }

    public int getReportingYear() {

        return reportingYear;
    }

    public void setReportingYear(int reportingYear) {

        this.reportingYear = reportingYear;
    }

    public boolean isPassed() {

        return passed;
    }

    public void setPassed(boolean passed) {

        this.passed = passed;
    }

    public EisDataReportDto withAgencyFacilityId(final String agencyFacilityId) {

        setAgencyFacilityId(agencyFacilityId);
        return this;
    }

    public EisDataReportDto withComments(final String comments) {

        setComments(comments);
        return this;
    }

    public EisDataReportDto withEisProgramId(final String eisProgramId) {

        setEisProgramId(eisProgramId);
        return this;
    }

    public EisDataReportDto withFacilityName(final String facilityName) {

        setFacilityName(facilityName);
        return this;
    }

    public EisDataReportDto withLastSubmissionStatus(final EisSubmissionStatus lastSubmissionStatus) {

        setLastSubmissionStatus(lastSubmissionStatus);
        return this;
    }

    public EisDataReportDto withLastTransactionId(final String lastTransactionId) {

        setLastTransactionId(lastTransactionId);
        return this;
    }

    public EisDataReportDto withPassed(final boolean passed) {

        setPassed(passed);
        return this;
    }

    public EisDataReportDto withReportingYear(final int reportingYear) {

        setReportingYear(reportingYear);
        return this;
    }

    public static class FromEntity implements Function<EmissionsReport, EisDataReportDto> {

        @Override
        public EisDataReportDto apply(EmissionsReport report) {

            FacilitySite facilitySite = report.getFacilitySites().get(0);

            return new EisDataReportDto()
                .withFacilityName(facilitySite.getName())
                .withAgencyFacilityId(facilitySite.getAltSiteIdentifier())
                .withEisProgramId(report.getEisProgramId())
                .withReportingYear(report.getYear())
                .withLastSubmissionStatus(report.getEisLastSubmissionStatus())
                .withLastTransactionId(report.getEisLastTransactionId())
                .withPassed(report.isEisPassed())
                .withComments(report.getEisComments());
        }
    }
}
