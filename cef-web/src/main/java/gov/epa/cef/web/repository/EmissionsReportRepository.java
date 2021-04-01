package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.dto.EisDataCriteria;
import gov.epa.cef.web.service.dto.EisDataStatsDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmissionsReportRepository extends CrudRepository<EmissionsReport, Long>, ProgramIdRetriever {

    /**
     * Find reports for a given eisProgramId
     * @param eisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByEisProgramId(String eisProgramId);
    
    /**
     * Find reports for a given masterFacilityRecordId
     * @param id
     * @return
     */
    List<EmissionsReport> findByMasterFacilityRecordId(Long id);

    /**
     * Find reports for a given eisProgramId  with the specified order
     * @param eisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByEisProgramId(String eisProgramId, Sort sort);

    /**
     * Find report for a given masterFacilityRecordId with the specified order
     * @param id
     * @param sort
     * @return
     */
    List<EmissionsReport> findByMasterFacilityRecordId(Long id, Sort sort);


    
    /***
     * Retrieve facilities based on master facility record id and status
     * @param masterFacilityRecordId
     * @param status (APPROVED, IN_PROGRESS, etc)
     * @return
     */
    @Query("select r from EmissionsReport r where r.masterFacilityRecord.id = :masterFacilityId and r.status = gov.epa.cef.web.domain.ReportStatus.IN_PROGRESS")
    List<EmissionsReport> findInProgressByMasterFacilityId(@NotNull Long masterFacilityId);

    /**
     * Find the report for a specified master facility record id for the specified year
     * @param masterFacilityRecordId
     * @param year
     * @return
     */
    Optional<EmissionsReport> findByMasterFacilityRecordIdAndYear(@NotNull Long masterFacilityRecordId, @NotNull Short year);

    /**
     * Find the most recent report for the specified master facility record id before the specified year
     * @param masterFacilityRecordId
     * @param year
     * @return
     */
    Optional<EmissionsReport> findFirstByMasterFacilityRecordIdAndYearLessThanOrderByYearDesc(@NotNull Long masterFacilityRecordId, @NotNull Short year);


    @Query("select r from EmissionsReport r where r.programSystemCode.code = :#{#crit.programSystemCode} and r.year = :#{#crit.reportingYear} and r.status = gov.epa.cef.web.domain.ReportStatus.APPROVED")
    Collection<EmissionsReport> findEisDataByYearAndNotComplete(@Param("crit") EisDataCriteria criteria);

    @Query("select r from EmissionsReport r where r.programSystemCode.code = :#{#crit.programSystemCode} and r.year = :#{#crit.reportingYear} and r.eisLastSubmissionStatus = :#{#crit.submissionStatus} and r.status = gov.epa.cef.web.domain.ReportStatus.APPROVED")
    Collection<EmissionsReport> findEisDataByYearAndStatus(@Param("crit") EisDataCriteria criteria);

    @Query("select r.eisLastSubmissionStatus as status, count(r.id) as count from EmissionsReport r where r.year = :year and r.programSystemCode.code = :programSystemCode and r.status = gov.epa.cef.web.domain.ReportStatus.APPROVED group by r.eisLastSubmissionStatus")
    Collection<EisDataStatsDto.EisDataStatusStat> findEisDataStatusesByYear(@Param("programSystemCode") String programSystemCode, @Param("year") Short year);

    @Query("select distinct r.year from EmissionsReport r where r.programSystemCode.code = :programSystemCode and r.status = gov.epa.cef.web.domain.ReportStatus.APPROVED")
    Collection<Integer> findEisDataYears(@Param("programSystemCode") String programSystemCode);

    @Cacheable(value = CacheName.ReportMasterIds)
    @Query("select mfr.id from EmissionsReport r join r.masterFacilityRecord mfr where r.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);
}
