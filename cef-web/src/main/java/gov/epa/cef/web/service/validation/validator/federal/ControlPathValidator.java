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
	
	
    	List<String> controlMeasureCodeList = new ArrayList<String>(); 
    	controlMeasureCodeList = controlMeasureCodeListBuilder(controlPath.getAssignments());
    	
		Map<Object, List<String>> cmMap = controlMeasureCodeList.stream()
				.collect(Collectors.groupingBy(cm -> cm));
		
		for (List<String> cmList: cmMap.values()) {
			if (cmList.size() > 1) {
 				result = false;
 				context.addFederalError(
 	  			ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
 	  			"controlPath.assignment.duplicate",
 	  			createValidationDetails(controlPath)
 	  			,cmList.get(0));
					
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
        
	return result;
  }
	
	private ValidationDetailDto createValidationDetails(ControlPath source) {

	    String description = MessageFormat.format("ControlPath: {0}", source.getPathId());
	
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
    
	private List<String> controlMeasureCodeListBuilder(List<ControlAssignment> controlAssignments){
    	List<String> controlMeasureCodeList = new ArrayList<String>(); 
    	for(ControlAssignment ca: controlAssignments){
    		if(ca.getControl() != null){
    			controlMeasureCodeList.add(ca.getControl().getControlMeasureCode().getDescription());
    		}
    		if(ca.getControlPathChild() != null){
    			controlMeasureCodeList.addAll(controlMeasureCodeListBuilder(ca.getControlPathChild().getAssignments()));
    		}
    	}
    	return controlMeasureCodeList;
	}

}
