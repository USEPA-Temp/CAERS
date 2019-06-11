package gov.epa.cef.web.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.Facility;

public interface FacilityRepository extends CrudRepository<Facility, Long> {

    /**
     * Retrieve facilities by frs facility and emissions report
     * @param frsFacilityId
     * @param emissionsReportId
     * @return
     */
    List<Facility> findByFrsFacilityIdAndEmissionsReportId(String frsFacilityId, Long emissionsReportId);
    
    /**
     * Retrieve facilities by eis program and emissions report
     * @param eisProgramId
     * @param emissionsReportId
     * @return
     */
    List<Facility> findByEisProgramIdAndEmissionsReportId(String eisProgramId, Long emissionsReportId);

    /***
     * Retrieve the common form facilities based on a given state code
     * @param 2 character state code
     * @return
     */
    List<Facility> findByStateCode(String stateCode);

}
