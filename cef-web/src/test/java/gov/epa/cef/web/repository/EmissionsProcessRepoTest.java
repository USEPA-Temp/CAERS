package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CommonInitializers;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class EmissionsProcessRepoTest extends BaseRepositoryTest {

    @Autowired
    EmissionsProcessRepository processRepo;

    @Autowired
    EmissionRepository emissionRepo;

    /**
     * Verify that deleting an emission process works and that any child emissions
     * are also deleted
     * @throws Exception
     */
    @Test
    public void deletingProcess_should_DeleteChildEmissions() throws Exception {

        //verify the process and emissions exist
        Optional<EmissionsProcess> process = processRepo.findById(9999991L);
        assertEquals(true, process.isPresent());

        Optional<Emission> emission = emissionRepo.findById(9999991L);
        assertEquals(true, emission.isPresent());

        //delete the process and verify that the emissions are gone as well
        processRepo.deleteById(9999991L);

        //verify the process and emissions exist
        process = processRepo.findById(9999991L);
        assertEquals(false, process.isPresent());

        emission = emissionRepo.findById(9999991L);
        assertEquals(false, emission.isPresent());

    }

}
