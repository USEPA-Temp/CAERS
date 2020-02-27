package gov.epa.cef.web.service.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import gov.epa.cef.web.exception.BulkReportValidationException;
import gov.epa.cef.web.service.dto.bulkUpload.BaseWorksheetDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
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

        List<WorksheetError> violations = new ArrayList<>();
        WorksheetDtoValidator worksheetValidator = new WorksheetDtoValidator(this.validator, violations);

        Consumer<FacilitySiteBulkUploadDto> siteIdCheck = new FacilityIdValidator(report, violations);

        report.getFacilitySites().forEach(siteIdCheck.andThen(worksheetValidator));
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

        if (violations.size() > 0) {

            throw new BulkReportValidationException(violations);
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
