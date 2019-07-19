package gov.epa.cef.web.service;

import java.util.List;
import gov.epa.cef.web.service.dto.ReportSummaryDto;

public interface ReportService {

    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param reportYear
     * @param facilitySiteId
     * @return
     */
    List<ReportSummaryDto> findByReportYearAndFacilitySiteId(Short reportYear, Long facilitySiteId);

}