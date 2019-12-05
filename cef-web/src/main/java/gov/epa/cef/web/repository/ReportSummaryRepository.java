package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.ReportSummary;

public interface ReportSummaryRepository extends CrudRepository<ReportSummary, Long> {

    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param reportYear
     * @param facilitySiteId
     * @return
     */
    List<ReportSummary> findByReportYearAndFacilitySiteId(Short reportYear, Long facilitySiteId, Sort sort);

}