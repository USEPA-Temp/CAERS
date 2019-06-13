package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.ReportingPeriod;

public interface ReportingPeriodRepository extends CrudRepository<ReportingPeriod, Long> {

    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    List<ReportingPeriod> findByEmissionsProcessId(Long processId);

}
