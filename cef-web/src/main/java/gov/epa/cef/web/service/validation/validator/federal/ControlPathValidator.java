package gov.epa.cef.web.service.validation.validator.federal;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

@Component
public class ControlPathValidator extends BaseValidator<ControlPath> {
	
	@Override
	public boolean validate(ValidatorContext validatorContext, ControlPath controlPath) {
		
	boolean result = true;
	CefValidatorContext context = getCefValidatorContext(validatorContext);
	
		List<ControlAssignment> controlAssignmentList = new ArrayList<ControlAssignment>();
		controlAssignmentList = controlAssignmentListBuilder(controlPath.getAssignments());
		
		Map<Object, List<ControlAssignment>> cdMap = controlAssignmentList.stream()
				.filter(cd -> (cd.getControl() != null))
				.collect(Collectors.groupingBy(cd -> cd.getControl().getId()));
		
		for (List<ControlAssignment> cdList: cdMap.values()) {
			if (cdList.size() > 1) {
				
				result = false;
					context.addFederalError(
		  			ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
		  			"controlPath.assignment.controlDevice.duplicate",
		  			createValidationDetails(controlPath),
		  			cdList.get(0).getControl().getIdentifier(),
		  			cdList.get(0).getControl().getControlMeasureCode().getDescription());
			}
		}
		
        Map<Object, List<ControlAssignment>> cpDuplicateMap = controlPath.getAssignments().stream()
        		.filter(cpa -> (cpa.getControlPathChild() != null))
                .collect(Collectors.groupingBy(ca -> ca.getControlPath().getAssignments()));
        
        for (List<ControlAssignment> cpList: cpDuplicateMap.values()) {
            if (cpList.size() > 1) {
		
                 result = false;
                 context.addFederalError(
                   ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
                   "controlPath.assignment.controlPath.duplicate",
                   createValidationDetails(controlPath),
                   cpList.get(0).getControlPathChild().getPathId());
                    
            }
        }
	
		if(controlPath.getReleasePointAppts().isEmpty()){
        	result = false;
        	context.addFederalWarning(
        			ValidationField.CONTROL_PATH_RPA_WARNING.value(),
        			"controlPath.releasePointApportionment.notAssigned",
        			createValidationDetails(controlPath));
		}

    	List<Control> controls = new ArrayList<Control>(); 
		List<Control> controlsList = buildAssignedControlsList(controlPath.getAssignments(), controls);
		
        Map<Object, List<ControlAssignment>> caMap = controlPath.getAssignments().stream()
                .filter(cpa -> (cpa.getControl() != null))
                .collect(Collectors.groupingBy(cpa -> cpa));
        
        	if ((caMap.size() == 0) && (controlsList.size() == 0)) {
            	result = false;
            	context.addFederalError(
            			ValidationField.CONTROL_PATH_NO_CONTROL_DEVICE_ASSIGNMENT.value(),
            			"controlPath.assignment.notAssigned",
            			createValidationDetails(controlPath));
        	}
        	
        List<ControlAssignment> sequenceNullMap = controlPath.getAssignments().stream()
                .filter(cpa -> (cpa.getSequenceNumber() == null))
                .collect(Collectors.toList());
         
        for (ControlAssignment ca: sequenceNullMap) {
        	result = false;
        	context.addFederalError(
        			ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
        			"controlPath.assignment.sequenceNumber.required",
        			createValidationDetails(controlPath, ca));
    	}
                
        List<ControlAssignment> caPathAndControlNullMap = controlPath.getAssignments().stream()
                .filter(cpa -> (cpa.getControl() == null && cpa.getControlPathChild() == null))
                .collect(Collectors.toList());
        
        for (ControlAssignment ca: caPathAndControlNullMap) {
        	result = false;
        	context.addFederalError(
        			ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
        			"controlPath.assignment.pathOrControl.required",
        			createValidationDetails(controlPath));
        }
        
	return result;
  }
	
	private ValidationDetailDto createValidationDetails(ControlPath source) {

	    String description = MessageFormat.format("ControlPath: {0}", source.getPathId());
	
	    ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getPathId(), EntityType.CONTROL_PATH, description);
	    return dto;
	}
	
	private ValidationDetailDto createValidationDetails(ControlPath source, ControlAssignment assignment) {

		String description;
		
		if (assignment.getControl() != null) {
		    description = MessageFormat.format("Control Path: {0}, Control Path Assignment: {1}", source.getPathId(), assignment.getControl().getIdentifier());
		}
		else {
		    description = MessageFormat.format("Control Path: {0}, Control Path Assignment: {1}", source.getPathId(), assignment.getControlPathChild().getPathId());
		}
	
	    ValidationDetailDto dto = new ValidationDetailDto(source.getId(), source.getPathId(), EntityType.CONTROL_PATH, description);
	    return dto;
	}
	
    private List<Control> buildAssignedControlsList(List<ControlAssignment> controlAssignments,List<Control> controls) {
    	for(ControlAssignment ca: controlAssignments){
    		if(ca.getControl() != null) {
    			controls.add(ca.getControl());
    			return controls;
    		}
			if(ca.getControlPathChild() != null) {
				buildAssignedControlsList(ca.getControlPathChild().getAssignments(), controls);
			}
    	}
    	return controls;
    }
    
    private List<ControlAssignment> controlAssignmentListBuilder(List<ControlAssignment> controlAssignments){
    	List<ControlAssignment> controlAssignmentList = new ArrayList<ControlAssignment>(); 
    	for(ControlAssignment ca: controlAssignments){
    		if(ca.getControl() != null){
    			controlAssignmentList.add(ca);
    		}
    		if(ca.getControlPathChild() != null){
    			controlAssignmentList.addAll(controlAssignmentListBuilder(ca.getControlPathChild().getAssignments()));
    		}
    	}
    	return controlAssignmentList;
	}
    
}
