package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.mapper.ControlMapper;

@Service
public class ControlServiceImpl implements ControlService {

    @Autowired
    private ControlRepository repo;

    @Autowired
    private ControlMapper mapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ControlService#retrieveById(java.lang.Long)
     */
    @Override
    public ControlDto retrieveById(Long id) {
        Control result = repo
            .findById(id)
            .orElse(null);
        return mapper.toDto(result);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ControlService#retrieveForFacilitySite(java.lang.Long)
     */
    @Override
    public List<ControlDto> retrieveForFacilitySite(Long facilitySiteId) {
        List<Control> result = repo.findByFacilitySiteId(facilitySiteId);
        return mapper.toDtoList(result);
    }

}
