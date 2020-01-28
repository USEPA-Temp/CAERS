package gov.epa.cef.web.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.ReportHistory;

public interface ReportHistoryRepository extends CrudRepository<ReportHistory, Long> {

    /***
     * Return list of report history records 
     * @param emissionsReportId
     * @return
     */
    List<ReportHistory> findByEmissionsReportIdOrderByActionDate(Long emissionsReportId);
    
    /**
    * Return the latest submission date for a given report
    * @param id Report ID
    * @return 
    */ 
   @Query("select max(actionDate) from ReportHistory rp where rp.emissionsReport.id = :id and rp.reportAction = gov.epa.cef.web.domain.ReportAction.SUBMITTED")
   Optional<Date> retrieveMaxSubmissionDateByReportId(@Param("id") Long id);

}