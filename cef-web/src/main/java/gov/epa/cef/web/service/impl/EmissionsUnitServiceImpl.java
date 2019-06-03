package gov.epa.cef.web.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.service.EmissionsUnitService;

@Service
public class EmissionsUnitServiceImpl implements EmissionsUnitService {

    @Autowired
    private EmissionsUnitRepository unitRepo;


    /**
     * Retrieve Emissions Unit by its id
     * @param unitId 
     * @return
     */	
    public EmissionsUnit retrieveUnitById(Long unitId) {
        return unitRepo
            .findById(unitId)
            .orElse(null);
    }


    @Override
    public Collection<EmissionsUnit> retrieveByFacilityId(Long facilityId) {
        return unitRepo.findByFacilityId(facilityId);
    }

}
