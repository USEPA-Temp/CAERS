package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.FacilitySite;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilitySiteRepository extends CrudRepository<FacilitySite, Long>, ProgramIdRetriever {

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
    @Cacheable(value = CacheName.FacilityProgramIds)
    @Query("select fs.eisProgramId from FacilitySite fs where fs.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
