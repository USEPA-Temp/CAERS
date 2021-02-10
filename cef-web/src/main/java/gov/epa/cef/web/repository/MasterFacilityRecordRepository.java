package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.common.BaseLookupEntity;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MasterFacilityRecordRepository extends JpaRepository<MasterFacilityRecord, Long>, ProgramIdRetriever {

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

    List<MasterFacilityRecord> findByProgramSystemCodeCode(String programSystemCode);

    /**
     * Retrieve program system codes currently in use; sort does not work on this query
     */
    @Query("select distinct programSystemCode FROM MasterFacilityRecord")
    List<BaseLookupEntity> findDistinctProgramSystems();

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.MasterFacilityProgramIds)
    @Query("select mfr.eisProgramId from MasterFacilityRecord mfr where mfr.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    @Cacheable(value = CacheName.MasterFacilityMasterIds)
    @Query("select mfr.id from MasterFacilityRecord mfr where mfr.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

}
