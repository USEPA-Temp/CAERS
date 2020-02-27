package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.EmissionsReport;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface EmissionsReportRepository extends CrudRepository<EmissionsReport, Long>, ProgramIdRetriever {

    /**
     * Find reports for a given facility
     * @param frsFacilityId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByFrsFacilityId(String frsFacilityId);

    /**
     * Find reports for a given facility with the specified order
     * @param frsFacilityId {@link ProgramFacility}'s programId
     * @param sort
     * @return
     */
    List<EmissionsReport> findByFrsFacilityId(String frsFacilityId, Sort sort);

    /**
     * Find reports for a given eisProgramId
     * @param eisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByEisProgramId(String eisProgramId);

    /**
     * Find reports for a given eisProgramId  with the specified order
     * @param eisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByEisProgramId(String eisProgramId, Sort sort);


    /**
     *
     * @param eisProgramId
     * @param year
     * @return
     */
    Optional<EmissionsReport> findByEisProgramIdAndYear(@NotBlank String eisProgramId, @NotNull Short year);

    /**
     * Find the most recent report for the specified program id before the specified year
     * @param eisProgramId
     * @param year
     * @return
     */
    Optional<EmissionsReport> findFirstByEisProgramIdAndYearLessThanOrderByYearDesc(@NotBlank String eisProgramId, @NotNull Short year);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.ReportProgramIds)
    @Query("select r.eisProgramId from EmissionsReport r where r.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
