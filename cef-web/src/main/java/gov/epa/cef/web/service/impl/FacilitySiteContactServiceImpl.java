package gov.epa.cef.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.repository.FacilitySiteContactRepository;
import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;
import gov.epa.cef.web.service.mapper.FacilitySiteContactMapper;

@Service
public class FacilitySiteContactServiceImpl implements FacilitySiteContactService {

    @Autowired
    private FacilitySiteContactRepository contactRepo;

    @Autowired
    private FacilitySiteContactMapper mapper;

    @Override
    public FacilitySiteContactDto retrieveById(Long id) {
        FacilitySiteContact result = contactRepo
                .findById(id)
                .orElse(null);
        return mapper.toDto(result);
    }

    @Override
    public List<FacilitySiteContactDto> retrieveForFacilitySite(Long facilitySiteId) {
        List<FacilitySiteContact> result = contactRepo.findByFacilityId(facilitySiteId);
        return result.stream()
                .map(contact -> mapper.toDto(contact))
                .collect(Collectors.toList());
    }


}