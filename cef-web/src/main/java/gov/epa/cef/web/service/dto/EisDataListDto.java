package gov.epa.cef.web.service.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EisDataListDto {

    private final EisDataCriteria criteria;

    private final List<EisDataReportDto> reports;

    public EisDataListDto(EisDataCriteria criteria) {

        this.criteria = criteria;
        this.reports = new ArrayList<>();
    }

    public EisDataCriteria getCriteria() {

        return criteria;
    }

    public Collection<EisDataReportDto> getReports() {

        return reports;
    }

    public void setReports(Collection<EisDataReportDto> reports) {

        this.reports.clear();
        if (reports != null) {

            this.reports.addAll(reports);
        }
    }

    public EisDataListDto withReports(Collection<EisDataReportDto> reports) {

        setReports(reports);
        return this;
    }
}

