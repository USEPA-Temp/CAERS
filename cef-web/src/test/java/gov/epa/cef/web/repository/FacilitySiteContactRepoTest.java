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
import gov.epa.cef.web.domain.FacilitySiteContact;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class FacilitySiteContactRepoTest extends BaseRepositoryTest {

    @Autowired
    FacilitySiteContactRepository facilitySiteContactRepo;
    
    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();
    }
    
    /**
     * Verify deleting an facility contact
     * @throws Exception
     */
    @Test
    public void deletingContact_should_return_false_WhenContactsDoNotExist() throws Exception {
        
        //verify the facility contact exist
        Optional<FacilitySiteContact> contact = facilitySiteContactRepo.findById(9999991L);
        assertEquals(true, contact.isPresent());
        
        //delete the contact
        facilitySiteContactRepo.deleteById(9999991L);
        
        //verify the facility contact does not exist
        contact = facilitySiteContactRepo.findById(9999991L);
        assertEquals(false, contact.isPresent());
    }
       
}
