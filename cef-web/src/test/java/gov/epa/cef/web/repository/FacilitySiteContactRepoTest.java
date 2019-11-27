package gov.epa.cef.web.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import gov.epa.cef.web.config.CommonInitializers;
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.FipsStateCode;

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
     * Verify creating a facility contact
     * @throws Exception
     */
    @Test
    public void createContactTest() throws Exception {
        
        //delete the all contacts from facility 9999992
    	facilitySiteContactRepo.deleteAll(facilitySiteContactRepo.findByFacilitySiteId(9999992L));
        
        List<FacilitySiteContact> contactList = facilitySiteContactRepo.findByFacilitySiteId(9999992L);
        assertEquals(0, contactList.size());
        
        //create new contact
        FacilitySiteContact contact = newContact();
        
        facilitySiteContactRepo.save(contact);
        
        //verify the facility contact created
        contactList = facilitySiteContactRepo.findByFacilitySiteId(9999992L);
        
        assertEquals(1, contactList.size());
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
    
    
    private FacilitySiteContact newContact() {
    	
    	FacilitySite facilitySite = new FacilitySite();
        facilitySite.setId(9999992L);
        
        ContactTypeCode contactTypeCode = new ContactTypeCode();
        contactTypeCode.setCode("FAC");
        
        FipsStateCode stateCode = new FipsStateCode();
        stateCode.setUspsCode("GA");
        
        FipsStateCode mailingStateCode = new FipsStateCode();
        mailingStateCode.setUspsCode("GA");        
        
    	FacilitySiteContact contact = new FacilitySiteContact();
    	
    	contact.setFacilitySite(facilitySite);
    	contact.setType(contactTypeCode);
    	contact.setFirstName("Ice");
        contact.setLastName("Bear");
        contact.setEmail("ice.bear@test.com");
        contact.setPhone("1234567890");
        contact.setPhoneExt("");
        contact.setStreetAddress("123 Test Street");
        contact.setCity("Fitzgerald");
        contact.setStateCode(stateCode);
        contact.setPostalCode("31750");
        contact.setCounty("Whitfield");
        contact.setMailingStreetAddress("123 Test Street");
        contact.setMailingCity("Fitzgerald");
        contact.setMailingStateCode(mailingStateCode);
        contact.setMailingPostalCode("31750");

        return contact;
    }
}
