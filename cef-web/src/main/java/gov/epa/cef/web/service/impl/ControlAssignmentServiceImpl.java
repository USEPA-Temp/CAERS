package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.service.ControlAssignmentService;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.mapper.ControlAssignmentMapper;

@Service
public class ControlAssignmentServiceImpl implements ControlAssignmentService {

    @Autowired
    private ControlAssignmentRepository repo;

    @Autowired
    private ControlAssignmentMapper mapper;

    @Override
    public ControlAssignmentDto retrieveById(Long id) {
        ControlAssignment result = repo
            .findById(id)
            .orElse(null);
        return mapper.toDto(result);
    }

    @Override
    public List<ControlAssignmentDto> retrieveForFacilitySite(Long facilitySiteId) {
        List<ControlAssignment> result = repo.findByControlFacilitySiteId(facilitySiteId);
        return mapper.toDtoList(result);
    }

    @Override
    public List<ControlAssignmentDto> retrieveForEmissionsProcess(Long processId) {
        List<ControlAssignment> result = repo.findByEmissionsProcessId(processId);
        return mapper.toDtoList(result);
    }

    @Override
    public List<ControlAssignmentDto> retrieveForEmissionsUnit(Long unitId) {
        List<ControlAssignment> result = repo.findByEmissionsUnitId(unitId);
        return mapper.toDtoList(result);
    }

    @Override
    public List<ControlAssignmentDto> retrieveForReleasePoint(Long pointId) {
        List<ControlAssignment> result = repo.findByReleasePointId(pointId);
        return mapper.toDtoList(result);
    }

}
