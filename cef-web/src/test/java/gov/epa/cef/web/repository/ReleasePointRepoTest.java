package gov.epa.cef.web.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import gov.epa.cef.web.config.CommonInitializers;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.ReleasePoint;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class ReleasePointRepoTest extends BaseRepositoryTest {

    @Autowired
    ReleasePointRepository rpRepo;
    
    @Autowired
    EmissionsProcessRepository processRepo;

    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();
    }
    
    /**
     * Verify that deleting a release point works and any child apportioned processes
     * are also deleted
     * @throws Exception
     */
    @Test
    public void deletingReleasePoint_should_DeleteChildApportionedProcesses() throws Exception {
        
        //verify the unit and process exist
        Optional<ReleasePoint> releasePoint = rpRepo.findById(9999991L);
        assertEquals(true, releasePoint.isPresent());
        
        List<EmissionsProcess> process = processRepo.findByReleasePointApptsReleasePointId(9999991L);
        assertEquals(true, process.size()>0);
        
        //delete the unit and verify that the processes are gone as well
        rpRepo.deleteById(9999991L);
        
        //verify the unit and process exist
        releasePoint = rpRepo.findById(9999991L);
        assertEquals(false, releasePoint.isPresent());
        
        process = processRepo.findByReleasePointApptsReleasePointId(9999991L);
        assertEquals(0, process.size());
        
    }
       
}
