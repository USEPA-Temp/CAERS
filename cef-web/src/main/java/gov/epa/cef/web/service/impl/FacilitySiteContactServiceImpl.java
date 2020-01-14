package gov.epa.cef.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.repository.ContactTypeCodeRepository;
import gov.epa.cef.web.repository.FacilitySiteContactRepository;
import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;
import gov.epa.cef.web.service.mapper.FacilitySiteContactMapper;

@Service
public class FacilitySiteContactServiceImpl implements FacilitySiteContactService {

    @Autowired
    private FacilitySiteContactRepository contactRepo;
    
    @Autowired
    private ContactTypeCodeRepository typeRepo;

    @Autowired
    private FacilitySiteContactMapper mapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;
    
    @Autowired
    private LookupServiceImpl lookupService;
    
    private static final String EMISSIONS_INVENTORY_CONTACT_TYPE_CODE = "EI";
    
    /**
     * Create a new Facility Site Contact from a DTO object
     */
    public FacilitySiteContactDto create(FacilitySiteContactDto dto) {
    	
    	FacilitySiteContact facilityContact = mapper.fromDto(dto);
    	
    	FacilitySiteContactDto result = mapper.toDto(contactRepo.save(facilityContact));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), FacilitySiteContactRepository.class);
    	return result;
    }
    
    @Override
    public FacilitySiteContactDto retrieveById(Long id) {
        FacilitySiteContact result = contactRepo
                .findById(id)
                .orElse(null);
        return mapper.toDto(result);
    }

    @Override
    public List<FacilitySiteContactDto> retrieveForFacilitySite(Long facilitySiteId) {
        List<FacilitySiteContact> result = contactRepo.findByFacilitySiteId(facilitySiteId);
        return mapper.toDtoList(result);
    }
    
    /**
     * Update an existing Facility Site Contact from a DTO
     */
    public FacilitySiteContactDto update(FacilitySiteContactDto dto) {
    	
    	FacilitySiteContact facilityContact = contactRepo.findById(dto.getId()).orElse(null);
    	mapper.updateFromDto(dto, facilityContact);
    	
    	FacilitySiteContactDto result = mapper.toDto(contactRepo.save(facilityContact));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), FacilitySiteContactRepository.class);

        return result;
    }
    
    public void delete(Long id) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(id), FacilitySiteContactRepository.class);
    	contactRepo.deleteById(id);
    }
    
    @Override
    public List<FacilitySiteContactDto> retrieveInventoryContactsForFacility(Long facilitySiteId) {
    	ContactTypeCode eiContactType = lookupService.retrieveContactTypeEntityByCode(EMISSIONS_INVENTORY_CONTACT_TYPE_CODE);
        List<FacilitySiteContact> result = contactRepo.findByFacilitySiteIdAndType(facilitySiteId, eiContactType);
        return mapper.toDtoList(result);
    }


}