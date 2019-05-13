package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.report.EmissionsReport;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

public interface EmissionsReportRepository extends CrudRepository<EmissionsReport, Long> {
    
    /**
     * Find reports for a given facility
     * @param facilityId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReport> findByFacilityId(String facilityId);
    
    /**
     * Find reports for a given facility with the specified order
     * @param facilityId {@link ProgramFacility}'s programId 
     * @param sort
     * @return
     */
    List<EmissionsReport> findByFacilityId(String facilityId, Sort sort);

}
