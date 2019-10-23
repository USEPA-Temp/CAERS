package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.FacilitySite;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface FacilitySiteRepository extends CrudRepository<FacilitySite, Long> {

    /**
     * Retrieve facilities by eis program and emissions report
     * @param eisProgramId
     * @param emissionsReportId
     * @return
     */
    List<FacilitySite> findByEisProgramIdAndEmissionsReportId(String eisProgramId, Long emissionsReportId);

    /***
     * Retrieve the common form facilities based on a given state code
     * @param stateCode : 2 character state code
     * @return
     */
    List<FacilitySite> findByStateCode(String stateCode);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    String findEisProgramIdById(Long id);
}
