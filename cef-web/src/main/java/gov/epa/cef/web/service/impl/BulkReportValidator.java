package gov.epa.cef.web.service.impl;

import com.google.common.base.MoreObjects;  
import com.google.common.base.Strings;

import gov.epa.cef.web.exception.BulkReportValidationException;
import gov.epa.cef.web.service.dto.bulkUpload.BaseWorksheetDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Component
public class BulkReportValidator {

    static final String SPREADSHEET_MAJOR_VERSION = "2";

    private final Validator validator;

    @Autowired
    BulkReportValidator(Validator validator) {

        this.validator = validator;
    }

    public void validate(EmissionsReportBulkUploadDto report) {

        List<WorksheetError> violations = new ArrayList<>();

        String regex = String.format("^%s(\\.\\d+)?$", SPREADSHEET_MAJOR_VERSION);

        if (report.getVersions().isEmpty() || !report.getVersions().get(0).getVersion().matches(regex)) {

            String msg = "This spreadsheet is out of date. Please download the most recent version of the spreadsheet.";
            violations.add(new WorksheetError(WorksheetName.Version.toString(), -1, msg));
            throw new BulkReportValidationException(violations);
        }

        WorksheetDtoValidator worksheetValidator = new WorksheetDtoValidator(this.validator, violations);

        Consumer<FacilitySiteBulkUploadDto> siteIdCheck = new FacilityIdValidator(report, violations);
        Consumer<EmissionsUnitBulkUploadDto> emissionsUnitCheck = new EmissionsUnitValidator(violations);
        Consumer<EmissionsProcessBulkUploadDto> emissionsProcessCheck = new EmissionsProcessValidator(violations);
        Consumer<ReleasePointBulkUploadDto> releasePointCheck = new ReleasePointValidator(violations);
        Consumer<ControlBulkUploadDto> controlCheck = new ControlValidator(violations);
        Consumer<ControlPathBulkUploadDto> controlPathCheck = new ControlPathValidator(violations);
        Consumer<List <ControlAssignmentBulkUploadDto>> loopCheck = new ControlAssignmentLoopValidator(report, violations);
        Consumer<ControlAssignmentBulkUploadDto> controlAssignmentCheck = new ControlAssignmentValidator(violations);

        report.getFacilitySites().forEach(siteIdCheck.andThen(worksheetValidator));
        report.getEmissionsUnits().forEach(emissionsUnitCheck.andThen(worksheetValidator));
        report.getEmissionsProcesses().forEach(emissionsProcessCheck.andThen(worksheetValidator));
        report.getReleasePoints().forEach(releasePointCheck.andThen(worksheetValidator));
        report.getReleasePointAppts().forEach(worksheetValidator);
        report.getReportingPeriods().forEach(worksheetValidator);
        report.getOperatingDetails().forEach(worksheetValidator);
        report.getEmissions().forEach(worksheetValidator);
        report.getEmissionFormulaVariables().forEach(worksheetValidator);
        report.getControlPaths().forEach(controlPathCheck.andThen(worksheetValidator));
        report.getControls().forEach(controlCheck.andThen(worksheetValidator));
        loopCheck.accept(report.getControlAssignments());
        report.getControlAssignments().forEach(controlAssignmentCheck.andThen(worksheetValidator));
        report.getControlPollutants().forEach(worksheetValidator);
        report.getControlPathPollutants().forEach(worksheetValidator);
        report.getFacilityNAICS().forEach(worksheetValidator);
        report.getFacilityContacts().forEach(worksheetValidator);

        if (violations.size() > 0) {

            throw new BulkReportValidationException(violations);
        }
    }
    
    static class EmissionsUnitValidator implements Consumer<EmissionsUnitBulkUploadDto> {
    	
    	private final List<WorksheetError> violations;
    	
    	public EmissionsUnitValidator(List<WorksheetError> violations) {
            this.violations = violations;
        }
    	
    	List<String> checkedUnitIdentifierList = new ArrayList<String>();
    	
    	public void accept(EmissionsUnitBulkUploadDto unit) {

            if (unit.getUnitIdentifier() != null && !checkedUnitIdentifierList.contains(unit.getUnitIdentifier().trim().toLowerCase())) {
            	checkedUnitIdentifierList.add(unit.getUnitIdentifier().trim().toLowerCase());
            } else {
            	String msg = String.format("Emissions Unit Identifier '%s' must be unique within the facility.", unit.getUnitIdentifier());
                violations.add(new WorksheetError(unit.getSheetName(), unit.getRow(), msg));
            }
    	}
    }
    
    static class EmissionsProcessValidator implements Consumer<EmissionsProcessBulkUploadDto> {
    	
    	private final List<WorksheetError> violations;
    	
    	public EmissionsProcessValidator(List<WorksheetError> violations) {
            this.violations = violations;
        }
    	
    	HashMap<Long, List<String>> checkUnitIdentifierList = new HashMap<Long, List<String>>();
    	
    	public void accept(EmissionsProcessBulkUploadDto process) {
    		
    		if (process.getEmissionsUnitId() != null && process.getEmissionsProcessIdentifier() != null) {

	            if (checkUnitIdentifierList.isEmpty() || !checkUnitIdentifierList.containsKey(process.getEmissionsUnitId())) {
	            	
	            	List<String> processList = new ArrayList<>();
	            	processList.add(process.getEmissionsProcessIdentifier().trim().toLowerCase());
	            	checkUnitIdentifierList.put(process.getEmissionsUnitId(), processList);
	            	
	            } else {
	            	List<String> processList = checkUnitIdentifierList.get(process.getEmissionsUnitId());
	            	
	            	if (processList.contains(process.getEmissionsProcessIdentifier().trim().toLowerCase())) {
	            		String msg = String.format("Emissions Process Identifier '%s' must be unique within the Emissions Unit.", process.getEmissionsProcessIdentifier());
	                  violations.add(new WorksheetError(process.getSheetName(), process.getRow(), msg));
	            	}
	            } 
    		}
    	}
    }
    
    static class ReleasePointValidator implements Consumer<ReleasePointBulkUploadDto> {
    	
    	private final List<WorksheetError> violations;
    	
    	public ReleasePointValidator(List<WorksheetError> violations) {
            this.violations = violations;
        }
    	
    	List<String> checkedUnitIdentifierList = new ArrayList<String>();
    	
    	public void accept(ReleasePointBulkUploadDto releasePoint) {

            if (releasePoint.getReleasePointIdentifier() != null && !checkedUnitIdentifierList.contains(releasePoint.getReleasePointIdentifier().trim().toLowerCase())) {
            	checkedUnitIdentifierList.add(releasePoint.getReleasePointIdentifier().trim().toLowerCase());
            } else {
            	String msg = String.format("Release Point Identifier '%s' must be unique within the facility.", releasePoint.getReleasePointIdentifier());
                violations.add(new WorksheetError(releasePoint.getSheetName(), releasePoint.getRow(), msg));
            }
    	}
    }
    
    static class ControlValidator implements Consumer<ControlBulkUploadDto> {
    	
    	private final List<WorksheetError> violations;
    	
    	public ControlValidator(List<WorksheetError> violations) {
            this.violations = violations;
        }
    	
    	List<String> checkedControlIdentifierList = new ArrayList<String>();
    	
    	public void accept(ControlBulkUploadDto control) {

            if (control.getIdentifier() != null && !checkedControlIdentifierList.contains(control.getIdentifier().trim().toLowerCase())) {
            	checkedControlIdentifierList.add(control.getIdentifier().trim().toLowerCase());
            } else {
            	String msg = String.format("Control Identifier '%s' must be unique within the facility.", control.getIdentifier());
                violations.add(new WorksheetError(control.getSheetName(), control.getRow(), msg));
            }
    	}
    }
    
    static class ControlPathValidator implements Consumer<ControlPathBulkUploadDto> {
    	
    	private final List<WorksheetError> violations;
    	
    	public ControlPathValidator(List<WorksheetError> violations) {
            this.violations = violations;
        }
    	
    	List<String> checkedControlPathIdentifierList = new ArrayList<String>();
    	
    	public void accept(ControlPathBulkUploadDto controlPath) {

            if (controlPath.getPathId() != null && !checkedControlPathIdentifierList.contains(controlPath.getPathId().trim().toLowerCase())) {
            	checkedControlPathIdentifierList.add(controlPath.getPathId().trim().toLowerCase());
            } else {
            	String msg = String.format("Control Path Identifier '%s' must be unique within the facility.", controlPath.getPathId());
                violations.add(new WorksheetError(controlPath.getSheetName(), controlPath.getRow(), msg));
            }
    	}
    }
    
    static class ControlAssignmentLoopValidator implements Consumer<List <ControlAssignmentBulkUploadDto>> {

    	private final EmissionsReportBulkUploadDto report;
    	
    	private final List<WorksheetError> violations;
    	    	
        public ControlAssignmentLoopValidator(EmissionsReportBulkUploadDto report, List<WorksheetError> violations) {

            this.violations = violations;
            this.report = report;
        }
        
        @Override
        public void accept(List<ControlAssignmentBulkUploadDto> controlAssignments) {
        
        	List<String> parentPaths = buildParentPaths(controlAssignments);
        	List<String> caList = new ArrayList<String>();
            Set<String> assignmentTree = new HashSet<String>(); 
            List<String> childPathsList = new ArrayList<String>();
            List<String> checkedParentPaths = new ArrayList<String>();
            
        	controlAssignments.forEach(ca ->{
        		if(ca.getControlPathChildId() != null){
            		caList.add(ca.getControlPathId()+"/"+ca.getControlPathChildId());
        		}
        	});
        	
        	if(!parentPaths.isEmpty()){
        		for(String parent: parentPaths){
        			childPathsList.clear();
        			assignmentTree.clear();
        			checkedParentPaths.clear();
	        		if(parent != null){
	        			assignmentTree.add(parent);
	        			buildChildPaths(parent, caList, childPathsList, checkedParentPaths);
	        		}
	        		checkForLoops(parent, childPathsList, assignmentTree, caList, violations, report.getControlPaths());
        		}
        	}
        }
    }
    
    static List <String> buildParentPaths(List<ControlAssignmentBulkUploadDto> assignments){
    	List<String> parentPaths = new ArrayList<String>();
    	assignments.forEach(ca ->{
    		if(ca.getControlPathId() != null && !parentPaths.contains(ca.getControlPathId().toString())){
    			parentPaths.add(ca.getControlPathId().toString());
    		}
    	});
    	return parentPaths;
    }
    
    
    public static void buildChildPaths(String parentPath, List<String> assignments, List<String> childPathsList, List<String> checkedParentPaths){
    	List<String> childPaths = new ArrayList<String>();
    	for(String ca: assignments){
    		if(ca.contains(parentPath+"/") && !checkedParentPaths.contains(ca)){
    			childPaths.add(ca.substring(parentPath.length()+1));
    			childPathsList.add(ca.substring(parentPath.length()+1));
    			checkedParentPaths.add(ca);
    		}
    	}
    	
    	if(!childPaths.isEmpty()){
    		for(String cp: childPaths){
    			buildChildPaths(cp, assignments, childPathsList, checkedParentPaths);
    		}
    	}
    }
    
    static boolean checkForLoops(String parentPath, List<String> childPaths, Set<String> assignmentTree, List<String> assignments, List<WorksheetError> violations, List<ControlPathBulkUploadDto> controlPaths){

    	for(String cp: childPaths){
    		boolean added = assignmentTree.add(cp);
    		if(!added){
    		    ControlPathBulkUploadDto childPathDto = null;
    		    ControlPathBulkUploadDto parentPathDto = null;
    			for(ControlPathBulkUploadDto controlPath: controlPaths){
    				if(controlPath.getId().toString().contentEquals(cp)){
    					childPathDto = controlPath;
    				}
    				if(controlPath.getId().toString().contentEquals(parentPath)){
    					parentPathDto = controlPath;
    				}
    			}
    			String msg = String.format("Control Path '%s' is associated more than once with a control path in rows %s. "
    			                         + "A control path may be associated only once with another control path.",
                        parentPathDto.getPathId(), assignmentTree.toString());
    			violations.add(new WorksheetError("Control Assignments", childPathDto.getRow(), msg));
    			return true;
    		}
    	}
    	return false;
    }
    
    static class ControlAssignmentValidator implements Consumer<ControlAssignmentBulkUploadDto> {

        private final List<WorksheetError> violations;

        public ControlAssignmentValidator(List<WorksheetError> violations) {

            this.violations = violations;
        }

        @Override
        public void accept(ControlAssignmentBulkUploadDto controlAssignment) {

            if (controlAssignment.getControlId() != null && controlAssignment.getControlPathChildId() != null) {
            	
                String msg = String.format("A Control Path and a Control Device cannot both be assigned on the same Control Path Assignment row.");

                violations.add(new WorksheetError(controlAssignment.getSheetName(), controlAssignment.getRow(), msg));
            }
            
            if (controlAssignment.getControlPathId() != null && controlAssignment.getControlPathChildId() == null && controlAssignment.getControlId() == null) {
            	String msg = String.format("Control Path Assignment must contain at least one Control Path or Control Device.");

                violations.add(new WorksheetError(controlAssignment.getSheetName(), controlAssignment.getRow(), msg));
            }
        }
    }

    static class FacilityIdValidator implements Consumer<FacilitySiteBulkUploadDto> {

        private final EmissionsReportBulkUploadDto report;

        private final List<WorksheetError> violations;

        public FacilityIdValidator(EmissionsReportBulkUploadDto report, List<WorksheetError> violations) {

            this.violations = violations;
            this.report = report;
        }

        @Override
        public void accept(FacilitySiteBulkUploadDto facilitySite) {

            String blank = ":BLANK:";

            if (report.getMasterFacilityRecordId().equals(facilitySite.getMasterFacilityRecordId()) == false) {

                String val = Objects.toString(facilitySite.getMasterFacilityRecordId(), blank);
                String msg = String.format("The Master Facility Record ID '%s' indicated on the Facility Information tab does not match the MFR id '%s' for the facility for which you are attempting to upload a CAERS report.",
                    val, report.getFrsFacilityId());

                violations.add(new WorksheetError(facilitySite.getSheetName(), facilitySite.getRow(), msg));
            }

            if (Strings.emptyToNull(report.getEisProgramId()) != null && report.getEisProgramId().equals(facilitySite.getEisProgramId()) == false) {

                String val = MoreObjects.firstNonNull(Strings.emptyToNull(facilitySite.getEisProgramId()), blank);
                String msg = String.format("The EIS Program ID '%s' indicated on the Facility Information tab does not match the EIS Program ID '%s' for the facility for which you are attempting to upload a CAERS report.",
                    val, report.getEisProgramId());

                violations.add(new WorksheetError(facilitySite.getSheetName(), facilitySite.getRow(), msg));
            }

            if (report.getAltSiteIdentifier().equals(facilitySite.getAltSiteIdentifier()) == false) {

                String val = MoreObjects.firstNonNull(Strings.emptyToNull(facilitySite.getAltSiteIdentifier()), blank);
                String msg = String.format("The State Program ID '%s' indicated on the Facility Information tab does not match the State Program ID '%s' for the facility for which you are attempting to upload a CAERS report.",
                    val, report.getAltSiteIdentifier());

                violations.add(new WorksheetError(facilitySite.getSheetName(), facilitySite.getRow(), msg));
            }
        }
    }

    static class WorksheetDtoValidator implements Consumer<BaseWorksheetDto> {

        private final Validator validator;

        private final List<WorksheetError> violations;

        public WorksheetDtoValidator(Validator validator, List<WorksheetError> violations) {

            this.validator = validator;
            this.violations = violations;
        }

        @Override
        public void accept(BaseWorksheetDto dto) {

            this.validator.validate(dto).forEach(violation -> {

                violations.add(new WorksheetError(dto.getSheetName(), dto.getRow(), violation.getMessage()));
            });
        }
    }
}
