package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.OperatingDetail;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OperatingDetailRepository extends CrudRepository<OperatingDetail, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.OperatingDetailProgramIds)
    @Query("select fs.eisProgramId from OperatingDetail od join od.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where od.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for an Operating Detail
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.OperatingDetailEmissionsReportIds)
    @Query("select r.id from OperatingDetail od join od.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where od.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
