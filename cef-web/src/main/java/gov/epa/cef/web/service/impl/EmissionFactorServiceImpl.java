package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.repository.EmissionFactorRepository;
import gov.epa.cef.web.service.EmissionFactorService;
import gov.epa.cef.web.service.dto.EmissionFactorDto;
import gov.epa.cef.web.service.mapper.EmissionFactorMapper;

@Service
public class EmissionFactorServiceImpl implements EmissionFactorService {

    @Autowired
    private EmissionFactorRepository efRepo;
    
    @Autowired
    private EmissionFactorMapper mapper;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.EmissionFactorService#retrieveByExample(gov.epa.cef.web.service.dto.EmissionFactorDto)
     */
    @Override
    public List<EmissionFactorDto> retrieveByExample(EmissionFactorDto dto) {
        return mapper.toDtoList(efRepo.findAll(Example.of(mapper.fromDto(dto))));
    }
}
