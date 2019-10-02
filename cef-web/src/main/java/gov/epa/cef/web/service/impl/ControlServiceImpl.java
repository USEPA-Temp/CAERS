package gov.epa.cef.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.mapper.ControlMapper;

@Service
public class ControlServiceImpl implements ControlService {

    @Autowired
    private ControlRepository repo;
    
    @Autowired
    private ControlAssignmentRepository assignmentRepo;

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

    
    /***
     * Retrieve a DTO containing all of the related sub-facility components for the given control
     * @param controlId
     * @return
     */
    @Override
    public List<EmissionsReportItemDto> retrieveControlComponents(Long controlId) {
    	Control control = repo.findById(controlId).orElse(null);
    	List<EmissionsReportItemDto> emissionsReportItems = new ArrayList<EmissionsReportItemDto>();
    	populateComponents(control, emissionsReportItems);
    	
    	return emissionsReportItems;
    }

    
    /***
     * Populate the sub-facility component lists within the controlComponentDto object (for the control's path and parent paths).
     * @param control
     * @param controlComponentDto
     */
	private void populateComponents(Control control, List<EmissionsReportItemDto> emissionsReportItems) {
		for (ControlAssignment assignment : control.getAssignments()) {
			ControlPath path = assignment.getControlPath();
			List<ControlAssignment> parentAssignments = assignmentRepo.findByControlPathChildId(path.getId());
			for (ControlAssignment parentAssignment : parentAssignments) {
				if (parentAssignment.getControl() != null) {
					populateComponents(parentAssignment.getControl(), emissionsReportItems);
				}
			}
			
			for (ReleasePointAppt rpa : path.getReleasePointAppts()) {
				if (!IsDuplicateItem(rpa.getEmissionsProcess().getId(), "process", emissionsReportItems)) {
					EmissionsReportItemDto eri = new EmissionsReportItemDto();
					eri.setDescription(rpa.getEmissionsProcess().getDescription());
					eri.setId(rpa.getEmissionsProcess().getId());
					eri.setIdentifier(rpa.getEmissionsProcess().getEmissionsProcessIdentifier());
					eri.setType("process");
					emissionsReportItems.add(eri);
				}
				
				if (!IsDuplicateItem(rpa.getReleasePoint().getId(), "release", emissionsReportItems)) {
					EmissionsReportItemDto eri = new EmissionsReportItemDto();
					eri.setDescription(rpa.getReleasePoint().getDescription());
					eri.setId(rpa.getReleasePoint().getId());
					eri.setIdentifier(rpa.getReleasePoint().getReleasePointIdentifier());
					eri.setType("release");
					emissionsReportItems.add(eri);
				}
				
				if (!IsDuplicateItem(rpa.getEmissionsProcess().getEmissionsUnit().getId(), "emissionUnit", emissionsReportItems)) {
					EmissionsReportItemDto eri = new EmissionsReportItemDto();
					eri.setDescription(rpa.getEmissionsProcess().getEmissionsUnit().getDescription());
					eri.setId(rpa.getEmissionsProcess().getEmissionsUnit().getId());
					eri.setIdentifier(rpa.getEmissionsProcess().getEmissionsUnit().getUnitIdentifier());
					eri.setType("emissionUnit");
					emissionsReportItems.add(eri);
				}
			}
		}
		
	}



	/***
	 * Determine if the item already exists in the emissionsReportItems list
	 * @param emissionsUnits
	 * @param emissionsUnit
	 * @return
	 */
	private boolean IsDuplicateItem(Long id, String reportItemType, List<EmissionsReportItemDto> emissionsUnits) {
		if (emissionsUnits != null) {
			for (EmissionsReportItemDto itemDto : emissionsUnits) {
				if (id.equals(itemDto.getId()) && reportItemType.equals(itemDto.getType())) {
					return true;
				}
			}
		}
		return false;
	}
}
