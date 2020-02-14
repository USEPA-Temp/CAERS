package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.exception.BulkReportValidationException;
import gov.epa.cef.web.service.dto.bulkUpload.BaseWorksheetDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class BulkReportValidator {

    private final Validator validator;

    @Autowired
    BulkReportValidator(Validator validator) {

        this.validator = validator;
    }

    public void validate(EmissionsReportBulkUploadDto report) {

        WorksheetDtoValidator worksheetValidator = new WorksheetDtoValidator(this.validator);

        report.getFacilitySites().forEach(worksheetValidator);
        report.getEmissionsUnits().forEach(worksheetValidator);
        report.getEmissionsProcesses().forEach(worksheetValidator);
        report.getReleasePoints().forEach(worksheetValidator);
        report.getReleasePointAppts().forEach(worksheetValidator);
        report.getReportingPeriods().forEach(worksheetValidator);
        report.getOperatingDetails().forEach(worksheetValidator);
        report.getEmissions().forEach(worksheetValidator);
        report.getControlPaths().forEach(worksheetValidator);
        report.getControls().forEach(worksheetValidator);
        report.getControlAssignments().forEach(worksheetValidator);
        report.getControlPollutants().forEach(worksheetValidator);
        report.getFacilityNAICS().forEach(worksheetValidator);
        report.getFacilityContacts().forEach(worksheetValidator);

        if (worksheetValidator.hasViolations()) {

            throw new BulkReportValidationException(worksheetValidator.getViolations());
        }
    }

    private static class WorksheetDtoValidator implements Consumer<BaseWorksheetDto> {

        private final Validator validator;

        private final List<String> violations;

        public WorksheetDtoValidator(Validator validator) {

            this.validator = validator;
            this.violations = new ArrayList<>();
        }

        @Override
        public void accept(BaseWorksheetDto dto) {

            this.validator.validate(dto).forEach(violation -> {

                violations.add(String.format("Sheet: %s, Row: %d, Message: %s",
                    dto.getSheetName(), dto.getRow(), violation.getMessage()));
            });
        }

        public List<String> getViolations() {

            return violations;
        }

        public boolean hasViolations() {

            return this.violations.size() > 0;
        }
    }
}
