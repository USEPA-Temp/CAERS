package gov.epa.cef.web.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    DataSource dataSource;

    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    FacilitySiteContactRepository facilitySiteContactRepo;
    
    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();
        
        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
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
     * Verify update of a facility contact
     * @throws Exception
     */
    @Test
    public void updateContactTest() throws Exception {

    	//update contact information
		FacilitySiteContact updateContact = facilitySiteContactRepo.findById(9999994L)
				.orElseThrow(() -> new IllegalStateException("Facility contact 9999994L does not exist."));

		assertEquals("7062771300", updateContact.getPhone());

		updateContact.setPhone("0008675309");

		facilitySiteContactRepo.save(updateContact);

		//verify information was updated
		SqlParameterSource params = new MapSqlParameterSource().addValue("id", updateContact.getId());

		List<Map<String, Object>> contact = this.jdbcTemplate
				.queryForList("select * from facility_site_contact where id = :id", params);

		assertEquals(1, contact.size());
		assertEquals("0008675309", contact.get(0).get("phone"));
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
