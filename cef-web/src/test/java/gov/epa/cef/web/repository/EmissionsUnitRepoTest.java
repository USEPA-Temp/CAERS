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
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class EmissionsUnitRepoTest extends BaseRepositoryTest {

    @Autowired
    EmissionsUnitRepository unitRepo;
    
    @Autowired
    EmissionsProcessRepository processRepo;

    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();
    }
    
    /**
     * Verify that deleting an emission unit works and that any child emission processes
     * are also deleted
     * @throws Exception
     */
    @Test
    public void deletingEmissionsUnit_should_DeleteChildEmissionsProcesses() throws Exception {
        
        //verify the unit and process exist
        Optional<EmissionsUnit> unit = unitRepo.findById(9999991L);
        assertEquals(true, unit.isPresent());
        
        Optional<EmissionsProcess> process = processRepo.findById(9999991L);
        assertEquals(true, process.isPresent());
        
        //delete the unit and verify that the processes are gone as well
        unitRepo.deleteById(9999991L);
        
        //verify the unit and process exist
        unit = unitRepo.findById(9999991L);
        assertEquals(false, unit.isPresent());
        
        process = processRepo.findById(9999991L);
        assertEquals(false, process.isPresent());
        
    }
       
}
