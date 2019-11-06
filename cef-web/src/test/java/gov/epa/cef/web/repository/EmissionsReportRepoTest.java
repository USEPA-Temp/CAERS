package gov.epa.cef.web.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import gov.epa.cef.web.config.CommonInitializers;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReleasePoint;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class EmissionsReportRepoTest extends BaseRepositoryTest {

    @Autowired
    EmissionsReportRepository reportRepo;
    
    @Autowired
    FacilitySiteRepository facilitySiteRepo;
    
    @Autowired
    ReleasePointRepository releasePointRepo;
    
    @Autowired
    EmissionsUnitRepository unitRepo;

    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();
    }
    
    /**
     * Verify that deleting an emissions report works and that any children
     * are also deleted
     * @throws Exception
     */
    @Test
    public void deletingReport_should_DeleteChild() throws Exception {
        
        //verify the emissionsReport, facilitySite, releasePoint, and emissionsUnit exist
        Optional<EmissionsReport> report = reportRepo.findById(9999997L);
        assertEquals(true, report.isPresent());
        
        Optional<FacilitySite> facilitySite = facilitySiteRepo.findById(9999991L);
        assertEquals(true, facilitySite.isPresent());
        
        Optional<ReleasePoint> releasePoint = releasePointRepo.findById(9999991L);
        assertEquals(true, releasePoint.isPresent());
        
        Optional<EmissionsUnit> unit = unitRepo.findById(9999991L);
        assertEquals(true, unit.isPresent());
        
        //delete the emissions report and verify that the children are gone as well
        reportRepo.deleteById(9999997L);
        
        //verify the emissionsReport, facilitySite, releasePoint, and emissionsUnit no longer exist
        report = reportRepo.findById(9999997L);
        assertEquals(false, report.isPresent());
        
        facilitySite = facilitySiteRepo.findById(9999991L);
        assertEquals(false, facilitySite.isPresent());
        
        releasePoint = releasePointRepo.findById(9999991L);
        assertEquals(false, releasePoint.isPresent());
        
        unit = unitRepo.findById(9999991L);
        assertEquals(false, unit.isPresent());    
    }
       
}
