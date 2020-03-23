package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportingPeriodUpdateResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private ReportingPeriodDto reportingPeriod;
    private List<String> updatedEmissions = new ArrayList<>();
    private List<String> notUpdatedEmissions = new ArrayList<>();
    private List<String> failedEmissions = new ArrayList<>();

    public ReportingPeriodDto getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(ReportingPeriodDto reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public List<String> getUpdatedEmissions() {
        return updatedEmissions;
    }

    public void setUpdatedEmissions(List<String> updatedEmissions) {
        this.updatedEmissions = updatedEmissions;
    }

    public List<String> getNotUpdatedEmissions() {
        return notUpdatedEmissions;
    }

    public void setNotUpdatedEmissions(List<String> notUpdatedEmissions) {
        this.notUpdatedEmissions = notUpdatedEmissions;
    }

    public List<String> getFailedEmissions() {
        return failedEmissions;
    }

    public void setFailedEmissions(List<String> failedEmissions) {
        this.failedEmissions = failedEmissions;
    }
}
