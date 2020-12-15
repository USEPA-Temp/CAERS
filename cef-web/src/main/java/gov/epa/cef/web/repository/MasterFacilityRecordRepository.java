package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.MasterFacilityRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MasterFacilityRecordRepository extends CrudRepository<MasterFacilityRecord, Long>, ProgramIdRetriever {

    /**
     * Retrieve facility by eis program
     * @param eisProgramId
     * @return
     */
    Optional<MasterFacilityRecord> findByEisProgramId(String eisProgramId);

    /***
     * Retrieve the common form facilities based on a given state code
     * @param stateCode : 2 character state code
     * @return
     */
    List<MasterFacilityRecord> findByStateCode(String stateCode);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
//    @Cacheable(value = CacheName.FacilityProgramIds)
    @Query("select mfr.eisProgramId from MasterFacilityRecord mfr where mfr.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

}
