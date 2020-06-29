package gov.epa.cef.web.service.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class EisDataListDto implements Consumer<EisDataReportDto> {

    private final EisDataCriteria criteria;

    private final List<EisDataReportDto> reports;

    public EisDataListDto() {

        this(null);
    }

    public EisDataListDto(EisDataCriteria criteria) {

        this.criteria = criteria;
        this.reports = new ArrayList<>();
    }

    @Override
    public void accept(EisDataReportDto eisDataReportDto) {

        if (eisDataReportDto != null) {

            this.reports.add(eisDataReportDto);
        }
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

