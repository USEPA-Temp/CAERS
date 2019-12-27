package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.ReportHistory;

public interface ReportHistoryRepository extends CrudRepository<ReportHistory, Long> {

    /***
     * Return list of report history records 
     * @param emissionsReportId
     * @return
     */
    List<ReportHistory> findByEmissionsReportIdOrderByActionDate(Long emissionsReportId);

}