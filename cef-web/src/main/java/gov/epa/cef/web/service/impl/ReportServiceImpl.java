package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmissionsReportRepository erRepo;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findByFacilityId(java.lang.String)
     */
    @Override
    public List<EmissionsReport> findByFacilityId(String facilityId) {

        return erRepo.findByEisProgramId(facilityId);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findById(java.lang.Long)
     */
    @Override
    public EmissionsReport findById(Long id) {

        return erRepo
            .findById(id)
            .orElse(null);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findMostRecentByFacility(java.lang.String)
     */
    @Override
    public EmissionsReport findMostRecentByFacility(String facilityId) {

        return erRepo.findByEisProgramId(facilityId, new Sort(Sort.Direction.DESC, "year"))
                .stream().findFirst().orElse(null);
    }

}
