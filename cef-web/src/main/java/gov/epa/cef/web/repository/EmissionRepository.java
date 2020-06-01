package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.Emission;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmissionRepository extends CrudRepository<Emission, Long>, ProgramIdRetriever, ReportIdRetriever {
	
	/**
   * Find all Emissions with the specified report id
   * @param reportId
   * @return
   */
	@Query("select e from Emission e join e.reportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r "
			+ "where r.id = :reportId and ep.operatingStatusCode.code != 'TS' and ep.operatingStatusCode.code != 'PS'")	
	List<Emission> findAllByReportId(@Param("reportId") Long reportId);

	
	/**
   * Find all Emissions with the specified process id
   * @param processId
   * @param reportId
   * @return
   */
	@Query("select e from Emission e join e.reportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where ep.id = :processId and r.id = :reportId")	
	List<Emission> findAllByProcessIdReportId(@Param("processId") Long processId, @Param("reportId") Long reportId);

    /**
     * Retrieve a specific Emission for a specific year
     * @param pollutantCode
     * @param rpTypeCode
     * @param processIdentifier
     * @param unitIdentifier
     * @param eisProgramId
     * @param year
     * @return
     */
    @Query("select e from Emission e join e.reportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r "
            + "where  e.pollutant.pollutantCode = :pollutantCode and rp.reportingPeriodTypeCode.code = :rpTypeCode "
            + "and ep.emissionsProcessIdentifier = :processIdentifier and eu.unitIdentifier = :unitIdentifier "
            + "and fs.eisProgramId = :eisProgramId and r.year = :year")
    List<Emission> retrieveMatchingForYear(@Param("pollutantCode") String pollutantCode, @Param("rpTypeCode") String rpTypeCode,
            @Param("processIdentifier") String processIdentifier, @Param("unitIdentifier") String unitIdentifier,
            @Param("eisProgramId") String eisProgramId, @Param("year") Short year);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.EmissionProgramIds)
    @Query("select fs.eisProgramId from Emission e join e.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where e.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
    * Retrieve Emissions Report id for an Emission
    * @param id
    * @return Emissions Report id
    */
   @Cacheable(value = CacheName.EmissionEmissionsReportIds)
   @Query("select r.id from Emission e join e.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where e.id = :id")
   Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
