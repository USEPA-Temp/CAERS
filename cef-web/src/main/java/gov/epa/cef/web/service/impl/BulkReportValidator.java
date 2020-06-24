package gov.epa.cef.web.service.impl;

import com.google.common.base.MoreObjects; 
import com.google.common.base.Strings;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.exception.BulkReportValidationException;
import gov.epa.cef.web.service.dto.bulkUpload.BaseWorksheetDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Component
public class BulkReportValidator {

    private final Validator validator;

    @Autowired
    BulkReportValidator(Validator validator) {

        this.validator = validator;
    }

    public void validate(EmissionsReportBulkUploadDto report) {

        List<WorksheetError> violations = new ArrayList<>();
        WorksheetDtoValidator worksheetValidator = new WorksheetDtoValidator(this.validator, violations);

        Consumer<FacilitySiteBulkUploadDto> siteIdCheck = new FacilityIdValidator(report, violations);
        Consumer<List <ControlAssignmentBulkUploadDto>> loopCheck = new ControlAssignmentLoopValidator(report, violations);

        report.getFacilitySites().forEach(siteIdCheck.andThen(worksheetValidator));
        report.getEmissionsUnits().forEach(worksheetValidator);
        report.getEmissionsProcesses().forEach(worksheetValidator);
        report.getReleasePoints().forEach(worksheetValidator);
        report.getReleasePointAppts().forEach(worksheetValidator);
        report.getReportingPeriods().forEach(worksheetValidator);
        report.getOperatingDetails().forEach(worksheetValidator);
        report.getEmissions().forEach(worksheetValidator);
        report.getEmissionFormulaVariables().forEach(worksheetValidator);
        report.getControlPaths().forEach(worksheetValidator);
        report.getControls().forEach(worksheetValidator);
        loopCheck.accept(report.getControlAssignments());
        report.getControlAssignments().forEach(worksheetValidator);
        report.getControlPollutants().forEach(worksheetValidator);
        report.getFacilityNAICS().forEach(worksheetValidator);
        report.getFacilityContacts().forEach(worksheetValidator);

        if (violations.size() > 0) {

            throw new BulkReportValidationException(violations);
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
            
        	controlAssignments.forEach(ca ->{
        		if(ca.getControlPathChildId() != null){
            		caList.add(ca.getControlPathId()+"/"+ca.getControlPathChildId());
        		}
        	});

            if(!parentPaths.get(0).isEmpty()){
            	List<String> childPaths = buildChildPaths(parentPaths.get(0), caList);
            	assignmentTree.add(parentPaths.get(0));
            	childPaths.forEach(cp ->{
            		System.out.println("cp before fn start: "+cp);
            	});
            	checkForLoops(parentPaths.get(0), childPaths, assignmentTree, caList, violations);
            }

        	assignmentTree.forEach(assignment ->{
        		System.out.println("Assignment Tree outside of function : "+assignment);
        	});
        }
    }
    
    static List <String> buildParentPaths(List<ControlAssignmentBulkUploadDto> assignments){
    	List<String> parentPaths = new ArrayList<String>();
    	assignments.forEach(ca ->{
    		if(!parentPaths.contains(ca.getControlPathId())){
    			parentPaths.add(ca.getControlPathId());
    		}
    	});
    	return parentPaths;
    }
    
    public static List <String> buildChildPaths(String parentPath, List<String> assignments){
    	List<String> childPaths = new ArrayList<String>();
    	for(String ca: assignments){
    		if(ca.contains(parentPath+"/")){
    			childPaths.add(ca.substring(parentPath.length()+1));
    		}
    	}
    	return childPaths;
    }
    
    static boolean checkForLoops(String parentPath, List<String> childPaths, Set<String> assignmentTree, List<String> assignments, List<WorksheetError> violations){
    	for(String cp: childPaths){
    		System.out.println("attempting to add cp: "+cp);
    		boolean added = assignmentTree.add(cp);
    		if(added){
    			System.out.println("adding child path "+cp+" to assignmentTree");
    		} else {
    			System.out.println("error found with "+parentPath+" -> "+cp);
    			String msg = String.format("Control Path '%s' cannot be a child path of Control Path '%s'.",
                        cp, parentPath);        		
    			violations.add(new WorksheetError("Control Assignments", 1, msg));
    			return true;
    		}
    	}
    	for(String cp: childPaths){
    		String nextParentPath = cp;
    		System.out.println("new parent path "+cp);
    		List<String> nextChildPaths = buildChildPaths(nextParentPath, assignments);
    		for(String cp1: nextChildPaths){
    			System.out.println("next child paths in fn: "+cp1);
    		}
    		checkForLoops(nextParentPath, nextChildPaths, assignmentTree, assignments, violations);
    	}
    	assignmentTree.forEach(assignment ->{
    		System.out.println("Assignment Tree at end: "+assignment);
    	});
    	return false;
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

            if (report.getFrsFacilityId().equals(facilitySite.getFrsFacilityId()) == false) {

                String val = MoreObjects.firstNonNull(Strings.emptyToNull(facilitySite.getFrsFacilityId()), blank);
                String msg = String.format("The FRS Facility ID '%s' indicated on the Facility Information tab does not match the FRS id '%s' for the facility for which you are attempting to upload a CAERS report.",
                    val, report.getFrsFacilityId());

                violations.add(new WorksheetError(facilitySite.getSheetName(), facilitySite.getRow(), msg));
            }

            if (report.getEisProgramId().equals(facilitySite.getEisProgramId()) == false) {

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
