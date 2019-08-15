package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionsReport;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

public interface EmissionsReportRepository extends CrudRepository<EmissionsReport, Long> {
    
    /**
     * Find reports for a given facility
     * @param facilityId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByFrsFacilityId(String frsFacilityId);
    
    /**
     * Find reports for a given facility with the specified order
     * @param facilityId {@link ProgramFacility}'s programId 
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
    EmissionsReport findByEisProgramIdAndYear(String eisProgramId, Short year);

}
