package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;

@Service
public class EmissionsReportServiceImpl implements EmissionsReportService {

    @Autowired
    private EmissionsReportRepository erRepo;
    
    @Autowired
    private EmissionsReportMapper emissionsReportMapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findByFacilityId(java.lang.String)
     */
    @Override
    public List<EmissionsReportDto> findByFacilityEisProgramId(String facilityEisProgramId) {
        List<EmissionsReport> emissionReports= erRepo.findByEisProgramId(facilityEisProgramId);
        return emissionsReportMapper.toDtoList(emissionReports);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findById(java.lang.Long)
     */
    @Override
    public EmissionsReportDto findById(Long id) {

        EmissionsReport emissionsReport= erRepo
            .findById(id)
            .orElse(null);
        return emissionsReportMapper.toDto(emissionsReport);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportService#findMostRecentByFacility(java.lang.String)
     */
    @Override
    public EmissionsReportDto findMostRecentByFacilityEisProgramId(String facilityEisProgramId) {

        EmissionsReport emissionsReport=erRepo.findByEisProgramId(facilityEisProgramId, new Sort(Sort.Direction.DESC, "year"))
                .stream().findFirst().orElse(null);
        return emissionsReportMapper.toDto(emissionsReport);
    }

}
