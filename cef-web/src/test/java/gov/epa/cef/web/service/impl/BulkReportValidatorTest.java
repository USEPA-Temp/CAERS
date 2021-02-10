package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BulkReportValidatorTest {

    @Test
    public void testFacilityIdCheck() {

        String eis1 = "EISProgramId001";
        String eis2 = "EISProgramId002";
        Long mfr1 = 1L;
        Long mfr2 = 2L;
        String alt1 = "AltSiteId001";
        String alt2 = "AltSiteId002";

        EmissionsReportBulkUploadDto report = new EmissionsReportBulkUploadDto();
        report.setEisProgramId(eis1);
        report.setMasterFacilityRecordId(mfr1);
        report.setAltSiteIdentifier(alt1);

        FacilitySiteBulkUploadDto facilitySite = new FacilitySiteBulkUploadDto();
        facilitySite.setEisProgramId(eis2);
        facilitySite.setMasterFacilityRecordId(mfr2);
        facilitySite.setAltSiteIdentifier(alt2);

        List<WorksheetError> violations = new ArrayList<>();

        BulkReportValidator.FacilityIdValidator facilityIdValidator =
            new BulkReportValidator.FacilityIdValidator(report, violations);

        // both bad
        violations.clear();
        facilitySite.setAltSiteIdentifier(alt1);
        facilityIdValidator.accept(facilitySite);
        assertEquals(2, violations.size());

        // frs bad
        violations.clear();
        facilitySite.setEisProgramId(eis1);
        facilitySite.setAltSiteIdentifier(alt1);
        facilityIdValidator.accept(facilitySite);
        assertEquals(1, violations.size());

        // eis bad
        violations.clear();
        facilitySite.setEisProgramId(eis2);
        facilitySite.setMasterFacilityRecordId(mfr1);
        facilitySite.setAltSiteIdentifier(alt1);
        facilityIdValidator.accept(facilitySite);
        assertEquals(1, violations.size());

        // none bad
        violations.clear();
        facilitySite.setEisProgramId(eis1);
        facilitySite.setMasterFacilityRecordId(mfr1);
        facilitySite.setAltSiteIdentifier(alt1);
        facilityIdValidator.accept(facilitySite);
        assertTrue(violations.isEmpty());
    }
}
