package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacilitySiteContactApiTest extends BaseApiTest {

    @Mock
    private FacilitySiteContactService facilitySiteContactService;

    @InjectMocks
    private FacilitySiteContactApi facilitySiteContactApi;


    private FacilitySiteContactDto facilitySiteContact;

    private List<FacilitySiteContactDto> facilitySiteContacts;

    @Before
    public void init() {
        facilitySiteContact=new FacilitySiteContactDto();
        facilitySiteContacts=new ArrayList<>();

        when(facilitySiteContactService.retrieveById(123L)).thenReturn(facilitySiteContact);
        when(facilitySiteContactService.retrieveForFacilitySite(1L)).thenReturn(facilitySiteContacts);
    }

    @Test
    public void retrieveContact_Should_ReturnFacilitySiteContactObjectWithOkStatus_When_ValidIdPassed() {
        ResponseEntity<FacilitySiteContactDto> result=facilitySiteContactApi.retrieveContact(123L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(facilitySiteContact, result.getBody());
    }

    @Test
    public void retrieveContactsForFacility_Should_ReturnFacilitySiteContactsListWithOkStatus_When_ValidFacilityIdPassed() {
        ResponseEntity<Collection<FacilitySiteContactDto>> result=facilitySiteContactApi.retrieveContactsForFacility(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(facilitySiteContacts, result.getBody());
    }
}
