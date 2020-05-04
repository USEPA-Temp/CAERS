package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ReportingPeriod;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportingPeriodRepository extends CrudRepository<ReportingPeriod, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    List<ReportingPeriod> findByEmissionsProcessId(Long processId);

    /**
     * Return all Reporting Periods for a facility site
     * @param facilitySiteId
     * @return
     */
    @Query("select rp from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where fs.id = :facilitySiteId")
    List<ReportingPeriod> findByFacilitySiteId(Long facilitySiteId);

    /**
     * Find Reporting Period with the specified type, process identifier, unit identifier, EIS program id, and year.
     * This combination should be unique and can be used to find a specific Reporting Period for a specific year
     * @param typeCode
     * @param processIdentifier
     * @param unitIdentifier
     * @param eisProgramId
     * @param year
     * @return
     */
    @Query("select rp from ReportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r "
            + "where rp.reportingPeriodTypeCode.code = :typeCode and ep.emissionsProcessIdentifier = :processIdentifier and eu.unitIdentifier = :unitIdentifier "
            + "and fs.eisProgramId = :eisProgramId and r.year = :year")
    List<ReportingPeriod> retrieveByTypeIdentifierParentFacilityYear(@Param("typeCode") String typeCode,
            @Param("processIdentifier") String processIdentifier, @Param("unitIdentifier") String unitIdentifier,
            @Param("eisProgramId") String eisProgramId, @Param("year") Short year);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Override
    @Cacheable(value = CacheName.ReportingPeriodProgramIds)
    @Query("select fs.eisProgramId from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where rp.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Reporting Period
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ReportingPeriodEmissionsReportIds)
    @Query("select r.id from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where rp.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
