package gov.epa.cef.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.mapper.ReportingPeriodMapper;

@Service
public class ReportingPeriodServiceImpl implements ReportingPeriodService {

    @Autowired
    private ReportingPeriodRepository repo;

    @Autowired
    private ReportingPeriodMapper mapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportingPeriodService#retrieveById(java.lang.Long)
     */
    @Override
    public ReportingPeriodDto retrieveById(Long id) {
        ReportingPeriod result = repo
            .findById(id)
            .orElse(null);
        return mapper.toDto(result);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportingPeriodService#retrieveForReleasePoint(java.lang.Long)
     */
    @Override
    public List<ReportingPeriodDto> retrieveForReleasePoint(Long processId) {
        List<ReportingPeriod> result = repo.findByEmissionsProcessId(processId);
        return result.stream()
                .map(period -> mapper.toDto(period))
                .collect(Collectors.toList());
    }

}
