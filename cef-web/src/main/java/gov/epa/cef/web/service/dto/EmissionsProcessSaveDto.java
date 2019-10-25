package gov.epa.cef.web.service.dto;

import java.util.List;

public class EmissionsProcessSaveDto extends EmissionsProcessDto {

    private static final long serialVersionUID = 1L;

    private List<ReportingPeriodDto> reportingPeriods;

    public List<ReportingPeriodDto> getReportingPeriods() {
        return reportingPeriods;
    }

    public void setReportingPeriods(List<ReportingPeriodDto> reportingPeriods) {
        this.reportingPeriods = reportingPeriods;
    }

    public EmissionsProcessSaveDto withId(Long id) {

        setId(id);
        return this;
    }
}
