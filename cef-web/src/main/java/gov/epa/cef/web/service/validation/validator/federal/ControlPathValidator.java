package gov.epa.cef.web.service.validation.validator.federal;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.service.dto.EntityType;
import gov.epa.cef.web.service.dto.ValidationDetailDto;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.BaseValidator;

@Component
public class ControlPathValidator extends BaseValidator<ControlPath> {

	@Autowired
	private ControlAssignmentRepository assignmentRepo;

	private static final String STATUS_TEMPORARILY_SHUTDOWN = "TS";
    private static final String STATUS_PERMANENTLY_SHUTDOWN = "PS";

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
        		.collect(Collectors.groupingBy(ca -> ca.getControlPathChild().getId()));

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

		List<ControlAssignment> percentApptRange = controlPath.getAssignments().stream()
				.filter(appt -> (appt.getPercentApportionment() != null))
				.collect(Collectors.toList());

		for (ControlAssignment ca: percentApptRange) {
			if (ca.getPercentApportionment() < 0.1 || ca.getPercentApportionment() > 100) {
				result = false;
					context.addFederalError(
				  			ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
				  			"controlPath.assignment.percentApportionment.range",
				  			createValidationDetails(controlPath),
				  			ca.getControlPath().getPathId());
			}
		}

		// check if control path is a parent path without a rp appt, or if both parent path of control path and control path does not have rp appt
		List<ControlPath> contAssignList = new ArrayList<ControlPath>();
		contAssignList = buildParentPathsList(controlPath);

		List<ControlPath> cpMap = contAssignList.stream()
				.filter(cd -> (cd.getReleasePointAppts().size() > 0)).collect(Collectors.toList());

		if(controlPath.getReleasePointAppts().isEmpty()) {

			if(contAssignList.isEmpty() || cpMap.size() < 1){
				result = false;
	        	context.addFederalWarning(
		    			ValidationField.CONTROL_PATH_RPA_WARNING.value(),
		    			"controlPath.releasePointApportionment.notAssigned",
		    			createValidationDetails(controlPath));
			}
		}


    	List<Control> controls = new ArrayList<Control>();
		List<Control> controlsList = buildAssignedControlsList(controlPath.getAssignments(), controls);

		List<ControlAssignment> caList = controlPath.getAssignments().stream()
                .filter(cpa -> (cpa.getControl() != null))
                .collect(Collectors.toList());

        Map<Object, List<ControlAssignment>> caEmptyMap = caList.stream()
                .collect(Collectors.groupingBy(cpa -> cpa));

        List<ControlAssignment> caPSList = caList.stream()
    			.filter(c -> STATUS_PERMANENTLY_SHUTDOWN.contentEquals(c.getControl().getOperatingStatusCode().getCode()))
				.collect(Collectors.toList());

        // if no controls assigned or if only PS controls are assigned
    	if ((caEmptyMap.size() == 0 && controlsList.size() == 0)
    		|| (caList.size() > 0 && caList.size() == caPSList.size() && controlsList.size() > 0)) {
        	result = false;
        	context.addFederalError(
        			ValidationField.CONTROL_PATH_NO_CONTROL_DEVICE_ASSIGNMENT.value(),
        			"controlPath.assignment.notAssigned",
        			createValidationDetails(controlPath));
    	}

    	Map<Object, List<ControlPath>> controlPaths = controlPath.getFacilitySite().getControlPaths().stream()
            .filter(cp -> (cp.getPathId() != null))
            .collect(Collectors.groupingBy(cpi -> cpi.getPathId().toLowerCase()));
    	
    	for (List<ControlPath> cpList : controlPaths.values()) {
    	    if (cpList.size() > 1 && cpList.get(0).getPathId().toLowerCase().contentEquals(controlPath.getPathId().toLowerCase())) {
    	        result = false;
    	        context.addFederalError(
    	            ValidationField.CONTROL_PATH_IDENTIFIER.value(),
                    "controlPath.pathIdentifier.duplicate",
                    createValidationDetails(controlPath)
                );

            }
        }

    	if (controlPath.getPercentControl() != null && (controlPath.getPercentControl() < 1 || controlPath.getPercentControl() > 100)) {
            result = false;
            context.addFederalError(
	            ValidationField.CONTROL_PATH_PERCENT_CONTROL.value(),
	            "controlPath.percentControl.range",
	            createValidationDetails(controlPath));
        }

    	// if control assigned is PS status
    	if ((caPSList.size() > 0)) {
    		for (ControlAssignment ca: caPSList) {
            	result = false;
            	context.addFederalError(
            			ValidationField.CONTROL_PATH_NO_CONTROL_DEVICE_ASSIGNMENT.value(),
            			"controlPath.assignment.controlDevice.permShutdown",
            			createValidationDetails(controlPath), ca.getControl().getIdentifier());
    		}
    	}

    	List<ControlAssignment> caTSList = caList.stream()
    			.filter(c -> STATUS_TEMPORARILY_SHUTDOWN.contentEquals(c.getControl().getOperatingStatusCode().getCode()))
				.collect(Collectors.toList());



    	// if control assigned is TS status
    	if ((caTSList.size() > 0)) {
    		for (ControlAssignment ca: caTSList) {
            	result = false;
            	context.addFederalWarning(
            			ValidationField.CONTROL_PATH_NO_CONTROL_DEVICE_ASSIGNMENT.value(),
            			"controlPath.assignment.controlDevice.tempShutdown",
            			createValidationDetails(controlPath), ca.getControl().getIdentifier());
    		}
    	}

        List<ControlAssignment> sequenceMap = controlPath.getAssignments().stream()
                .filter(cpa -> (cpa.getSequenceNumber() != null))
                .collect(Collectors.toList());
        List<Integer> uniqueSequenceList = new ArrayList<Integer>();

        for (ControlAssignment ca: sequenceMap) {
        	if(!uniqueSequenceList.contains(ca.getSequenceNumber())){
        		uniqueSequenceList.add(ca.getSequenceNumber());
        	}
        }

        for (Integer sequenceNumber: uniqueSequenceList) {
        	Double totalApportionment = 0.0;
	        for (ControlAssignment ca: sequenceMap) {
	        	if(ca.getSequenceNumber() != null && ca.getSequenceNumber().equals(sequenceNumber)) {
		        	totalApportionment = ca.getPercentApportionment() + totalApportionment;
	        	}
	        }
        	if (totalApportionment != 100) {
            	result = false;
            	context.addFederalError(
            			ValidationField.CONTROL_PATH_ASSIGNMENT.value(),
            			"controlPath.assignment.sequenceNumber.totalApportionment",
            			createValidationDetails(controlPath),sequenceNumber);
            }
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

    private List<ControlPath> buildParentPathsList(ControlPath cp){
    	List<ControlPath> parentPathList = new ArrayList<ControlPath>();
		List<ControlAssignment> controlAssignmentList = assignmentRepo.findByControlPathChildId(cp.getId());
		parentPathList.add(cp);
		for(ControlAssignment c: controlAssignmentList){
			if(controlAssignmentList.size() > 0) {
				parentPathList.addAll(buildParentPathsList(c.getControlPath()));
	    	}
		}
    	return parentPathList;
    }

}
