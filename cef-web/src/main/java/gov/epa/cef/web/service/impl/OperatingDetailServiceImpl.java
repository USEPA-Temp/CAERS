package gov.epa.cef.web.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.repository.OperatingDetailRepository;
import gov.epa.cef.web.service.OperatingDetailService;
import gov.epa.cef.web.service.dto.OperatingDetailDto;
import gov.epa.cef.web.service.mapper.OperatingDetailMapper;


@Service
public class OperatingDetailServiceImpl implements OperatingDetailService {

    @Autowired
    private OperatingDetailRepository repo;

    @Autowired
    private OperatingDetailMapper mapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.OperatingDetailService#update(gov.epa.cef.web.service.dto.OperatingDetailDto)
     */
    @Override
    public OperatingDetailDto update(OperatingDetailDto dto) {

        OperatingDetail entity = repo.findById(dto.getId()).orElse(null);
        mapper.updateFromDto(dto, entity);

        OperatingDetailDto result = mapper.toDto(repo.save(entity));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), OperatingDetailRepository.class);
        return result;
    }

}
